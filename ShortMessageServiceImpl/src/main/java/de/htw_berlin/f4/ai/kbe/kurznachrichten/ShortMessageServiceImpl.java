package de.htw_berlin.f4.ai.kbe.kurznachrichten;


import java.util.*;

import de.htw_berlin.aStudent.model.MessageModel;
import de.htw_berlin.aStudent.model.TopicModel;
import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.aStudent.repository.MessageRepository;
import de.htw_berlin.aStudent.repository.TopicRepository;
import de.htw_berlin.aStudent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw_berlin.aStudent.service.AnApplicationService;
import org.springframework.transaction.annotation.Transactional;


public class ShortMessageServiceImpl implements ShortMessageService {

    @Autowired
    AnApplicationService anApplicationService;
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

    @Override
    public Long createMessage(String userName, String message, String topic) {
        if (message.length() < 10 || message.length() > 255) {
            throw new IllegalArgumentException("The length of the message should be between 10 and 255 characters");
        }
        if (!isUserExisting(userName)) {
            throw new IllegalArgumentException("User " + userName + "does not exist");
        }
        if (!isTopicExisting(topic)) {
            throw new IllegalArgumentException("The topic does not exist");
        }
        if (userName == null || message == null || topic == null) {
            throw new NullPointerException("The arguments should not be null");
        } else {
            MessageModel messageModel = new MessageModel();
            Calendar calender = Calendar.getInstance(Locale.GERMANY);
            Date timestamp = calender.getTime();
            messageModel.setContent(message);
            messageModel.setDate(timestamp);
            messageModel.setOrigin(true);
            messageModel.setTopic(topic);
            messageModel.setUser(getUserByName(userName));
            messageRepository.save(messageModel);
            // TODO How the fuck can i get the messageID ?

        }
        // TODO this is transient
        Long id = (long) 0;
        return id;
    }

    @Override
    public Long respondToMessage(String userName, String message,
                                 Long predecessor) {
        if (message.length() < 10 || message.length() > 255) {
            throw new IllegalArgumentException("The length of the message should be between 10 and 255 characters");
        }
        if (!isUserExisting(userName)) {
            throw new IllegalArgumentException("User " + userName + "does not exist");
        }
        if (messageRepository.findById(predecessor) == null) {
            throw new IllegalArgumentException("The predecessor does not exist");
        }
        if (!messageRepository.findById(predecessor).isOrigin()) {
            throw new IllegalArgumentException("The predecessor is not origin");
        }
        if (userName == null || message == null || predecessor == null) {
            throw new NullPointerException("The arguments should not be null");
        } else {
            Calendar calender = Calendar.getInstance(Locale.GERMANY);
            Date timestamp = calender.getTime();
            String topic = messageRepository.findById(predecessor).getTopic();
            MessageModel messageModel = new MessageModel();
            messageModel.setContent(message);
            messageModel.setDate(timestamp);
            messageModel.setOrigin(false);
            messageModel.setTopic(topic);
            messageModel.setUser(getUserByName(userName));
            messageRepository.save(messageModel);
            // TODO How the fuck can i get the messageID ?
        }
        // TODO this is transient
        Long id = (long) 0;
        return id;
    }

    @Override
    public void deleteMessage(String userName, Long messageId)
            throws AuthorizationException {
        if (!isMessageExisting(messageId)) {
            throw new IllegalArgumentException("Message does not exist");
        }
        if (!messageRepository.findById(messageId).isOrigin()) {
            throw new IllegalArgumentException("Message is not origin");
        }
        if (!isUserExisting(userName)) {
            throw new IllegalArgumentException("User " + userName + "does not exist");
        }
        if (userName == null || messageId == null) {
            throw new NullPointerException("The arguments should not be null");
        }
        if (messageRepository.findById(messageId).getUser().getName() != userName) {
            throw new AuthorizationException("The user " + userName + " is not the creator!");
        } else {
            messageRepository.deleteById(messageId);
        }
    }

    @Transactional
    @Override
    public void createTopic(String userName, String topic) {
        if (!isUserExisting(userName)) {
            throw new IllegalArgumentException("User " + userName + "does not exist");
        }
        if (isTopicExisting(topic)) {
            throw new IllegalArgumentException("Topic " + topic + "exists already!");
        }
        if (topic.length() < 2 || topic.length() > 70) {
            throw new IllegalArgumentException("The length of topic should be between 2 and 70 characters");
        }
        if (userName == null || topic == null) {
            throw new NullPointerException("The arguments should not be null");
        } else {
            TopicModel topicModel = new TopicModel();
            topicModel.setName(topic);
            topicRepository.save(topicModel);
        }

    }

    @Transactional(readOnly = true)
    private boolean isMessageExisting(Long id) {
        MessageModel messageModel = messageRepository.findById(id);
        return messageModel != null ? true : false;
    }

    @Transactional(readOnly = true)
    private boolean isTopicExisting(String topic) {
        Set<TopicModel> topicModelSet = topicRepository.findAll();
        boolean isExisting = false;
        for (TopicModel topicModel : topicModelSet) {
            isExisting = topicModel.getName() == topic ? true : isExisting;
        }
        return isExisting;
    }

    @Transactional
    @Override
    public Set<String> getTopics() {
        Set<TopicModel> topicModelSet = new HashSet<TopicModel>();
        topicModelSet = topicRepository.findAll();
        return anApplicationService.castTopicModelToString(topicModelSet);
    }

    @Transactional
    @Override
    public List<List<Message>> getMessageByTopic(
            String topic, Date since) {
        if (isTopicExisting(topic)) {
            throw new IllegalArgumentException("Topic " + topic + "exists already!");
        }
        if (topic == null) {
            throw new NullPointerException("Topic should not be null");
        } else {
            List<List<Message>> messagesByTopicList = new ArrayList<List<Message>>();
            for (MessageModel messageModel : messageRepository.findAll()) {
                if (messageModel.getTopic() == topic) {
                    if (messageModel.getDate().compareTo(since) > 0) {
                        // TODO Adde nur die ab since date
                    } else if (since == null) {
                       // TODO Adde Alle
                    }
                }

            }
        }

        return null;
    }

    @Transactional
    @Override
    public void createUser(String userName, String city) {
        if (userName.length() < 4 || userName.length() > 30) {
            throw new IllegalArgumentException("The length of the userName should be between 4 and 30 characters");
        }
        if (isUserExisting(userName)) {
            throw new IllegalArgumentException("The user is already existing");
        }
        if (userName == null || city == null) {
            throw new NullPointerException("The arguments should not be null");
        } else {
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
            throw new IllegalArgumentException("User " + userName + "does not exist");
        } else {
            Set<UserModel> userModelSet = userRepository.findAll();
            for (UserModel userModel : userModelSet) {
                if (userModel.getName() == userName) {
                    Long id = userModel.getId();
                    UserModel userModelDelete = userRepository.findById(id);
                    userRepository.delete(userModelDelete);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Set<User> getUsers() {
        Set<UserModel> userModelSet = userRepository.findAll();
        return anApplicationService.castModelToUser(userModelSet);
    }

    @Transactional(readOnly = true)
    private boolean isUserExisting(String userName) {
        boolean isUserExisting = false;
        Set<UserModel> userModelSet = userRepository.findAll();
        for (UserModel userModel : userModelSet) {
            isUserExisting = userModel.getName() == userName ? true : isUserExisting;
        }
        return isUserExisting;
    }


    // TODO should this method be part of the userRepository?
    @Transactional(readOnly = true)
    private User getUserByName(String userName) {
        User user = new User();
        Set<UserModel> userModelSet = userRepository.findAll();
        for (UserModel userModel : userModelSet) {
            if (userModel.getName() == userName) {
                user.setCity(userModel.getCity());
                user.setName(userModel.getName());
            }
        }
        return user;
    }
}