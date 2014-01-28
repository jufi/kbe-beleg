package de.htw_berlin.f4.ai.kbe.kurznachrichten;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw_berlin.aStudent.model.MessageModel;
import de.htw_berlin.aStudent.model.TopicModel;
import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.aStudent.repository.MessageRepository;
import de.htw_berlin.aStudent.repository.TopicRepository;
import de.htw_berlin.aStudent.repository.UserRepository;

@Service
public class ShortMessageServiceImpl implements ShortMessageService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	TopicRepository topicRepository;
	@Autowired
	MessageRepository messageRepository;

	/**
	 * Default Bean constructor
	 */
	public ShortMessageServiceImpl() {
	}

	public ShortMessageServiceImpl(MessageRepository messageRepository, TopicRepository topicRepository) {
		this.messageRepository = messageRepository;
		this.topicRepository = topicRepository;
	}

	@Transactional
	@Override
	public Long createMessage(String userName, String message, String topic) {
		Long id;
		if (userName == null || message == null || topic == null) {
			throw new NullPointerException("The arguments should not be null");
		}
		else if (message.length() < 10 || message.length() > 255) {
			throw new IllegalArgumentException("The length of the message should be between 10 and 255 characters");
		}
		else if (!isUserExisting(userName)) {
			throw new IllegalArgumentException("User " + userName + " does not exist");
		}
		else if (!isTopicExisting(topic)) {
			throw new IllegalArgumentException("The topic does not exist");
		}
		else {
			MessageModel messageModel = new MessageModel();
			Calendar calender = Calendar.getInstance(Locale.GERMANY);
			Date timestamp = calender.getTime();
			messageModel.setContent(message);
			messageModel.setDate(timestamp);
			messageModel.setOrigin(true);
			messageModel.setTopic(topic);
			messageModel.setUser(userRepository.getUserByName(userName));
			messageRepository.save(messageModel);
			id = messageRepository.getIDByMessageModel(messageModel);
		}
		return id;
	}

	@Transactional
	@Override
	public Long respondToMessage(String userName, String message, Long predecessor) {
		Long id;
		if (userName == null || message == null || predecessor == null) {
			throw new NullPointerException("The arguments should not be null");
		}
		else if (message.length() < 10 || message.length() > 255) {
			throw new IllegalArgumentException("The length of the message should be between 10 and 255 characters");
		}
		else if (!isUserExisting(userName)) {
			throw new IllegalArgumentException("User " + userName + " does not exist");
		}
		else if (messageRepository. findById(predecessor) == null) {
			throw new IllegalArgumentException("The predecessor does not exist");
		}
		else if (!messageRepository.findById(predecessor).isOrigin()) {
			throw new IllegalArgumentException("The predecessor is not origin");
		}
		else {
			String topic = messageRepository.findById(predecessor).getTopic();
			MessageModel messageModel = new MessageModel();
			messageModel.setContent(message);
			messageModel.setDate(new Date());
			messageModel.setOrigin(false);
			messageModel.setTopic(topic);
			messageModel.setUser(userRepository.getUserByName(userName));
			messageModel.setPredecessorId(predecessor);
			messageRepository.save(messageModel);
			id = messageRepository.getIDByMessageModel(messageModel);
		}
		return id;
	}

	@Transactional
	@Override
	public void deleteMessage(String userName, Long messageId) throws AuthorizationException {
		if (userName == null || messageId == null) {
			throw new NullPointerException("The arguments should not be null");
		}
		else if (!isMessageExisting(messageId)) {
			throw new IllegalArgumentException("Message does not exist");
		}
		else if (!messageRepository.findById(messageId).isOrigin()) {
			throw new IllegalArgumentException("Message is not origin");
		}
		else if (!isUserExisting(userName)) {
			throw new IllegalArgumentException("User " + userName + " does not exist");
		}
		else if (!messageRepository.findById(messageId).getUser().getName().equals(userName)) {
			throw new AuthorizationException("The user " + userName + " is not the creator!");
		}
		else {
			messageRepository.deleteById(messageId);
		}
	}

	@Transactional
	@Override
	public void createTopic(String userName, String topic) {
		if (userName == null || topic == null) {
			throw new NullPointerException("The arguments should not be null");
		}
		else if (!isUserExisting(userName)) {
			throw new IllegalArgumentException("User " + userName + " does not exist");
		}
		else if (isTopicExisting(topic)) {
			throw new IllegalArgumentException("Topic " + topic + " exists already!");
		}
		else if (topic.length() < 2 || topic.length() > 70) {
			throw new IllegalArgumentException("The length of topic should be between 2 and 70 characters");
		}
		else {
			TopicModel topicModel = new TopicModel();
			topicModel.setName(topic);
			topicRepository.save(topicModel);
		}
	}

	@Transactional(readOnly = true)
	private boolean isMessageExisting(Long id) {
		MessageModel messageModel = messageRepository.findById(id);
		return messageModel != null;
	}

	@Transactional(readOnly = true)
	private boolean isTopicExisting(String topic) {
		Set<TopicModel> topicModelSet = topicRepository.findAll();
		boolean isExisting = false;
		for (TopicModel topicModel : topicModelSet) {
			isExisting = topicModel.getName().equals(topic) ? true : isExisting;
		}
		return isExisting;
	}

	@Transactional
	@Override
	public Set<String> getTopics() {
		Set<TopicModel> topicModelSet = new HashSet<TopicModel>();
		topicModelSet = topicRepository.findAll();
		return topicRepository.castTopicModelToString(topicModelSet);
	}

	@Transactional
	@Override
	public List<List<Message>> getMessageByTopic(String topic, Date since) {
		List<List<Message>> messagesByTopicList = new ArrayList<List<Message>>();
		if (topic == null) {
			throw new NullPointerException("Topic should not be null");
		}
		else if (!isTopicExisting(topic)) {
			throw new IllegalArgumentException("Topic " + topic + " does not exist!");
		}
		else {
			List<MessageModel> messageModelList = new ArrayList<MessageModel>(messageRepository.findAll());
			Collections.sort(messageModelList);
			for (MessageModel originMessage : messageModelList) {
				if (originMessage.isOrigin() && originMessage.getTopic().equals(topic)) {
					List<Message> messageList = new ArrayList<Message>();
					if (since != null && (originMessage.getDate().compareTo(since) > 0)) {
						messageList.add(messageRepository.castModelToMessage(originMessage));
						addResponds(messageModelList, originMessage, messageList);
						messagesByTopicList.add(messageList);
					}
					else if (since == null) {
						messageList.add(messageRepository.castModelToMessage(originMessage));
						addResponds(messageModelList, originMessage, messageList);
						messagesByTopicList.add(messageList);
					}
				}
			}
		}
		return messagesByTopicList;
	}

	private void addResponds(List<MessageModel> messageModelList, MessageModel originMessage, List<Message> messageList) {
		for (MessageModel respondMessage : messageModelList) {
			if ((respondMessage.getPredecessorId() == originMessage.getMessageId()) && ! respondMessage.isOrigin()) {
				messageList.add(messageRepository.castModelToMessage(respondMessage));
			}
		}
	}

	@Transactional
	@Override
	public void createUser(String userName, String city) {
		if (userName == null || city == null) {
			throw new NullPointerException("The arguments should not be null");
		}
		else if (userName.length() < 4 || userName.length() > 30) {
			throw new IllegalArgumentException("The length of the userName should be between 4 and 30 characters");
		}
		else if (isUserExisting(userName)) {
			throw new IllegalArgumentException("The user " + userName + " is already existing");
		}
		else {
			UserModel userModel = new UserModel();
			userModel.setName(userName);
			userModel.setCity(city);
			userRepository.save(userModel);
		}
	}

	@Transactional
	@Override
	public void deleteUser(String userName) {
		if (!isUserExisting(userName)) {
			throw new IllegalArgumentException("User " + userName + " does not exist");
		}
		else {
			Set<UserModel> userModelSet = userRepository.findAll();
			for (UserModel userModel : userModelSet) {
				if (userModel.getName().equals(userName)) {
					Long id = userModel.getId();
					UserModel userModelDelete = userRepository.findById(id);
					for (MessageModel messageModel : messageRepository.findAll()) {
						if (messageModel.getUser().getName().equals(userName)) {
							messageRepository.delete(messageModel);
						}
					}
					userRepository.delete(userModelDelete);
				}
			}
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Set<User> getUsers() {
		Set<UserModel> userModelSet = userRepository.findAll();
		return userRepository.castModelToUser(userModelSet);
	}

	@Transactional(readOnly = true)
	private boolean isUserExisting(String userName) {
		boolean isUserExisting = false;
		Set<UserModel> userModelSet = userRepository.findAll();
		for (UserModel userModel : userModelSet) {
			isUserExisting = userModel.getName().equals(userName) ? true : isUserExisting;
		}
		return isUserExisting;
	}


}