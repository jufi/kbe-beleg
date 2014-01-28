package de.htw_berlin.f4.ai.kbe.kurznachrichten;
import java.util.*;
import de.htw_berlin.aStudent.model.MessageModel;
import de.htw_berlin.aStudent.model.TopicModel;
import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.aStudent.repository.MessageRepositoryImpl;
import de.htw_berlin.aStudent.repository.TopicRepositoryImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import static org.mockito.Mockito.*;

public class GetMessageByTopicMockTest {

	private ShortMessageServiceImpl shortMessageService;
	private MessageRepositoryImpl messageRepository;
	private TopicRepositoryImpl topicRepository;
	private UserModel userModel;
	private String topic;
	private List<List<Message>> messageList;

	@Before
	public void before() {
		userModel = new UserModel("Donald Duck", "Entenhausen");
		topic = "This is a awesome topic";
		prepareMessageRepository();
		prepareTopicRepository();
		shortMessageService = new ShortMessageServiceImpl(messageRepository, topicRepository);
	}

	private void prepareTopicRepository() {
		Set<TopicModel> topicSet = prepareTopics();
		topicRepository = mock(TopicRepositoryImpl.class);
		when(topicRepository.findAll()).thenReturn(topicSet);
	}

	private Set<TopicModel> prepareTopics() {
		Set<TopicModel> topicModelSet = new HashSet<>();
		TopicModel topicModel = new TopicModel(topic);
		topicModelSet.add(topicModel);
		return topicModelSet;
	}

	private void prepareMessageRepository() {
		messageList = new ArrayList<>();
		messageRepository = mock(MessageRepositoryImpl.class);
		Set<MessageModel> messageSet = prepareMessages();
		when(messageRepository.findAll()).thenReturn(messageSet);
		List<Message> innerList = new ArrayList<>();
		for (MessageModel messageModel : messageSet) {
			Message message = new Message();
			message.setDate(messageModel.getDate());
			message.setUser(messageModel.getUser());
			message.setTopic(messageModel.getTopic());
			message.setOrigin(messageModel.isOrigin());
			message.setContent(messageModel.getContent());
			when(messageRepository.castModelToMessage(messageModel)).thenReturn(message);
			innerList.add(message);
		}
		messageList.add(innerList);
	}

	private Set<MessageModel> prepareMessages() {
		Set<MessageModel> messageSet = new LinkedHashSet<MessageModel>();
		MessageModel messageModelOrigin = new MessageModel(new Date(), RandomStringUtils.random(25), topic, userModel, true);
		messageSet.add(messageModelOrigin);
		for (int i = 0; i < 5; i++) {
			MessageModel messageModel = new MessageModel(new Date(), RandomStringUtils.random(25), topic, userModel, false);
			messageSet.add(messageModel);
		}
		return messageSet;
	}

	@Test
	public void testGetMessageByTopicDateNull() {
		List<List<Message>> list = shortMessageService.getMessageByTopic(topic, null);
		Assert.assertArrayEquals(messageList.toArray(), list.toArray());
	}

	@Test
	public void testGetMessageByTopicDate() {

	}
}

