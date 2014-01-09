package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
* @author Kevin Goy and Julian Fink
*/
public abstract class ShortMessageServiceTest {

	private static ShortMessageService sms;
	private long validPredecessor;
	private long notExistingMessageId;


	@Before
	public void prepareSms() {
		Assert.assertTrue(
				"There should be no user before generating the first one and the return of the empty user list should not be null.",
				sms.getUsers().size() == 0);
		sms.createUser("Donald Duck", "Entenhausen");
		sms.createUser("Gustav Gans", "Entenhausen");
		Assert.assertTrue(
				"There should be no topic before generating the first one and the return of the empty topics should not be null.",
				sms.getTopics().size() == 0);

		sms.createTopic("Donald Duck", "Testing Interfaces");
		validPredecessor = sms.createMessage("Donald Duck", "This is a valid message", "Testing Interfaces");
		notExistingMessageId = 1337;
	}

	@Test
	public void testRespondToMessage() {
		String userName = "Donald Duck";
		String message = "This is valid message";
		Long respond = sms.respondToMessage(userName, message, validPredecessor);
		Assert.assertNotNull("There is no id for the responded message", respond);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRespondToMessageExceptionLongMessage() {
		String userName = "Donald Duck";
		String longMessage =
				"This is very long testmessage with more than 255 characters. When this message is passed, an Exception should be thrown.\n" +
						"Testing interfaces instead of implementations is much better. Even now the message is not long enough. But with this sentence, the limit should be reached.";
		sms.respondToMessage(userName, longMessage, validPredecessor);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRespondToMessageExceptionShortMessage() {
		String userName = "Donald Duck";
		String shortMessage = "To short";
		sms.respondToMessage(userName, shortMessage, validPredecessor);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRespondToMessageExceptionNoUser() {
		String userName = "Phantom Blot";
		String message = "This is valid message";
		Long predecessor = sms.createMessage(userName, "this is a valid message", "Testing Interfaces");
		sms.respondToMessage(userName, message, predecessor);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRespondToMessageExceptionNoPredecessor() {
		String userName = "Donald Duck";
		String message = "This is valid message";
		String topic = "Testing Interfaces";
		Long predecessor = sms.createMessage(userName, message, topic);
		try {
			sms.deleteMessage(userName, predecessor);
		}
		catch (AuthorizationException e) {
			e.printStackTrace();
		}
		//deleted message should not exist anymore
		sms.respondToMessage(userName, message, predecessor);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRespondToMessageExceptionMessageIsSuccessor() {
		String userName = "Donald Duck";
		String message = "This is valid message";
		String topic = "Testing Interfaces";
		Long predecessor = sms.createMessage(userName, message, topic);
		Long successor = sms.respondToMessage(userName, message, predecessor);
		sms.respondToMessage(userName, message, successor);
	}

	@Test(expected = NullPointerException.class)
	public void testRespondToMessageExceptionNullUser() {
		String message = "This is valid message";
		Long predecessor = sms.createMessage("Donald Duck", "this is a valid message", "Testing Interfaces");
		sms.respondToMessage(null, message, predecessor);
	}

	@Test(expected = NullPointerException.class)
	public void testRespondToMessageExceptionNullMessage() {
		String userName = "Donald Duck";
		Long predecessor = sms.createMessage(userName, "this is a valid message", "Testing Interfaces");
		sms.respondToMessage(userName, null, predecessor);
	}

	@Test(expected = NullPointerException.class)
	public void testRespondToMessageExceptionNullPredecessor() {
		String userName = "Donald Duck";
		String message = "This is valid message";
		sms.respondToMessage(userName, message, null);
	}

	@Test
	public void testCreateTopic() {
		String userName = "Donald Duck";
		String topic = "new Topic";
		int topicAmount = sms.getTopics().size();
		sms.createTopic(userName, topic);
		int amountAfterCreating = sms.getTopics().size();
		Assert.assertTrue("After creating a topic there should be one topic more than before.",
				amountAfterCreating == topicAmount + 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTopicNoUser() {
		String userName = "Phantom Blot";
		String topic = "topic";
		sms.createTopic(userName, topic);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTopicExistingTopic() {
		String userName = "Donald Duck";
		String topic = "Testing Interfaces";
		sms.createTopic(userName, topic);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTopicShortTopic() {
		String userName = "Donald Duck";
		String shortTopic = "a";
		sms.createTopic(userName, shortTopic);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTopicLongTopic() {
		String userName = "Donald Duck";
		String longTopic =
				"This is very long testmessage with more than 255 characters. When this message is passed, an Exception should be thrown.\n" +
						"Testing interfaces instead of implementations is much better. Even now the message is not long enough. But with this sentence, the limit should be reached.";
		sms.createTopic(userName, longTopic);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateTopicNullUser() {
		String topic = "Testing";
		sms.createTopic(null, topic);

	}

	@Test(expected = NullPointerException.class)
	public void testCreateTopicNullTopic() {
		String userName = "Donald Duck";
		sms.createTopic(userName, null);
	}


	@Test
	public void testGetMessageByTopic() {
		String topic = "Testing Interfaces";
		sms.createMessage("Donald Duck", "This is a message which is valid", topic);
		List<List<Message>> topicLists = sms.getMessageByTopic(topic, null);
		for (List<Message> messageList : topicLists) {
			for (Message message : messageList) {
				Assert.assertTrue("All messages should be from the determined topic", message.getTopic().equals(topic));
			}
		}
		Calendar calender = Calendar.getInstance(Locale.GERMANY);
		Date timestamp = calender.getTime();
		Long firstMessage =
				sms.createMessage("Donald Duck", "This is another message which is valid, but was created after the timestamp",
						topic);
		List<List<Message>> newTopicLists = sms.getMessageByTopic(topic, timestamp);
		int messageCounter = 0;
		for (List<Message> messageList : newTopicLists) {
			for (Message message : messageList) {
				messageCounter++;
			}
		}
		Assert.assertTrue("There should be just one message of this type after the timestamp.", messageCounter == 1);
		List<List<Message>> anotherTopicLists = sms.getMessageByTopic(topic, null);
		Assert.assertTrue("There should be 3 or more messages for that topic.", anotherTopicLists.size() >= 3);
		Assert.assertTrue("If there is no message the list should be empty.",
				sms.getMessageByTopic("reallynewTopic", null).isEmpty());
		Long secondMessage = sms.createMessage("Donald Duck",
				"This is another new message which is valid, but was created after the timestamp", topic);
		Long thirdMessage =
				sms.createMessage("Donald Duck", "This is third message which is valid, but was created after the timestamp",
						topic);
		List<List<Message>> orderedList = sms.getMessageByTopic(topic, timestamp);
		for (int i = 0; i < orderedList.size(); i++) {
			for (Message message : orderedList.get(i)) {
				if (i == 1) {
					Assert.assertEquals("The list should be ordered.", firstMessage, message.getMessageId());
				}
				else if (i == 2) {
					Assert.assertEquals("The list should be ordered.", secondMessage, message.getMessageId());
				}
				else if (i == 3) {
					Assert.assertEquals("The list should be ordered.", thirdMessage, message.getMessageId());
				}
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMessageByTopicNoTopic() {
		String nonExistingTopic = "This topic does not exist";
		sms.getMessageByTopic(nonExistingTopic, null);
	}

	@Test(expected = NullPointerException.class)
	public void testGetMessageByTopicNullTopic() {
		sms.getMessageByTopic(null, null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageToLong() {
		String userName = "Donald Duck";
		String message =
				"This is very long testmessage with more than 255 characters. When this message is passed, an Exception should be thrown.\n" +
						"Testing interfaces instead of implementations is much better. Even now the message is not long enough. But with this sentence, the limit should be reached.";
		String topic = "Testing Interfaces";
		sms.createMessage(userName, message, topic);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageToShort() {
		String userName = "Donald Duck";
		String message = "To short";
		String topic = "Testing Interfaces";
		sms.createMessage(userName, message, topic);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUserNotExist() {
		String userName = "Phantom Blot";
		String message = "This is valid message";
		String topic = "Testing Interfaces";
		sms.createMessage(userName, message, topic);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTopicNotExist() {
		String userName = "Donald Duck";
		String message = "This is valid message";
		String topic = "This topic does not exist";
		sms.createMessage(userName, message, topic);
	}

	@Test(expected = NullPointerException.class)
	public void testUserNameNull() {
		String message = "This is valid message";
		String topic = "Testing Interfaces";
		sms.createMessage(null, message, topic);

	}

	@Test(expected = NullPointerException.class)
	public void testMessageNull() {
		String userName = "Donald Duck";
		String topic = "Testing Interfaces";
		sms.createMessage(userName, null, topic);

	}

	@Test(expected = NullPointerException.class)
	public void testTopicNull() {
		String userName = "Donald Duck";
		String message = "This is valid message";
		sms.createMessage(userName, message, null);
	}

	@Test
	public void testDoesCreateMessageReturnNotNull() {
		String userName = "Donald Duck";
		String message = "This is valid message";
		String topic = "Testing Interfaces";
		Long id = sms.createMessage(userName, message, topic);
		Assert.assertNotNull("The returned datatype should not be null", id);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteMessageNotExist() {
		try {
			sms.deleteMessage("Donald Duck", notExistingMessageId);
		}
		catch (AuthorizationException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteMessageIsNotOrigin() {
		// A responding message is not a origin message
		Long notOriginMessageID = sms.respondToMessage("Gustav Gans", "This a valid responding message", validPredecessor);
		try {
			sms.deleteMessage("Gustav Gans", notOriginMessageID);
		}
		catch (AuthorizationException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteMessageUserNotExist() {
		Long originMessageId = sms.createMessage("Donald Duck", "This is a valid origin message", "Testing Interfaces");
		try {
			sms.deleteMessage("Phantom Blot", originMessageId);
		}
		catch (AuthorizationException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = NullPointerException.class)
	public void testDeleteMessageUserNameNull() {
		Long originMessageId = sms.createMessage("Donald Duck", "This is a valid origin message", "Testing Interfaces");
		try {
			sms.deleteMessage(null, originMessageId);
		}
		catch (AuthorizationException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = NullPointerException.class)
	public void testDeleteMessageIdNull() {
		try {
			sms.deleteMessage("Donald Duck", null);
		}
		catch (AuthorizationException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = AuthorizationException.class)
	public void testDeleteMessageUserNotCorrespondToCreatingUser() {
		sms.createUser("Daniel Duesentrieb", "Entenhausen");
		try {
			sms.deleteMessage("Daniel Duesentrieb", validPredecessor);
		}
		catch (AuthorizationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteMessage() {
		sms.createUser("Mickey Maus", "Entenhausen");
		long originMessageId = sms.createMessage("Mickey Maus", "This message will be deleted", "Testing Interfaces");
		try {
			sms.deleteMessage("Mickey Maus", originMessageId);
		}
		catch (AuthorizationException e) {
			e.printStackTrace();
		}

		Set<String> allTopics = sms.getTopics();
		for (String topics : allTopics) {
			List<List<Message>> outerMessageListByTopic = sms.getMessageByTopic(topics, null);
			for (List<Message> innerMessageLists : outerMessageListByTopic) {
				for (Message messages : innerMessageLists) {
					Assert.assertNotSame("The message should not exist anymore", originMessageId, messages.getMessageId());
				}
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUserAlreadyExist() {
		sms.createUser("Donald Duck", "Entenhausen");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUserNameToLong() {
		String userName = "Donald Fauntleroy Dagobert Duck";
		sms.createUser(userName, "Entenhausen");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUserNameToShort() {
		String userName = "Tic";
		sms.createUser(userName, "Entenhausen");
	}

	@Test(expected = NullPointerException.class)
	public void testCreateUserNameIsNull() {
		sms.createUser(null, "Entenhausen");
	}

	@Test(expected = NullPointerException.class)
	public void testCreateUserCityIsNull() {
		sms.createUser("Donald Duck", null);
	}

	@Test
	public void testCreateUserValid() {
		String userName = "Dagobert Duck";
		String cityName = "Entenhausen";
		sms.createUser(userName, cityName);

		Set<User> userList = sms.getUsers();
		Boolean userExist = false;
		for (User user : userList) {
			userExist = (((user.getName() == userName) && (user.getCity() == cityName)) ? true : userExist);
		}
		Assert.assertTrue("The should be a user with the userName" + userName + "who lives in " + cityName, userExist);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteUserNotExist() {
		sms.deleteUser("Zacharias Zorngiebel");
	}

	@Test
	public void testDeleteUserValid() {
		String userName = "Dussel Duck";
		sms.createUser(userName, "Entenhausen");
		sms.deleteUser(userName);

		Set<User> userList = sms.getUsers();
		Boolean userExists = false;
		for (User user : userList) {
			userExists = user.getName() == userName ? true : userExists;
		}
		Assert.assertFalse("The user should exists anymore", userExists);

	}
}