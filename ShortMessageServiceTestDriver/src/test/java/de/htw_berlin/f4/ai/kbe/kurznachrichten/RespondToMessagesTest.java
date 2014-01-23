package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class RespondToMessagesTest extends TestMessageInit{


	
	@Test(expected=IllegalArgumentException.class)
	public void testRespondToMessageTooLong() {
		shortMessageService.respondToMessage(userName, RandomStringUtils.random(9), messageId);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRespondToMessageTooShort() {
		shortMessageService.respondToMessage(userName, RandomStringUtils.random(256), messageId);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRespondToMessageUserDoesNotExist() {
		shortMessageService.respondToMessage(RandomStringUtils.random(10), RandomStringUtils.random(20), messageId);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRespondToMessagePredecessorDoesNotExist() {
		shortMessageService.respondToMessage(userName, RandomStringUtils.random(20), 736332L);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRespondToMessageIsResponse() {		
		Long responseMessageId = shortMessageService.respondToMessage(userName, RandomStringUtils.random(20), messageId);
		shortMessageService.respondToMessage(userName, RandomStringUtils.random(20), responseMessageId);		
	}
	
	@Test(expected=NullPointerException.class)
	public void testRespondToMessageUserIsNull() {
		shortMessageService.respondToMessage(null, RandomStringUtils.random(20), messageId);
	}
	
	@Test(expected=NullPointerException.class)
	public void testRespondToMessageMessageIsNull() {
		shortMessageService.respondToMessage(userName , null, messageId);
	}
	
	@Test(expected=NullPointerException.class)
	public void testRespondToMessageMessageIdIsNull() {
		shortMessageService.respondToMessage(userName, RandomStringUtils.random(20), null);
	}
	
		
	//getMessageByTopic Tests:
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetMessageByTopicTopicDoesNotExist() {
		shortMessageService.getMessageByTopic(RandomStringUtils.random(10), null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetMessageByTopicIsNull() {
		shortMessageService.getMessageByTopic(null, null);
	}
	
	
	@Test
	public void testSimpleGetMessageByTopic() {
	
		String content = RandomStringUtils.random(20);
		Long responseId = shortMessageService.respondToMessage(userName, content, messageId);		
		List<List<Message>> messages = shortMessageService.getMessageByTopic(topic, null);		
		
		assertEquals(1, messages.size());	
		assertEquals(2, messages.get(0).size());	
		
		Message m = messages.get(0).get(0);
		Message r = messages.get(0).get(1);
		
		assertEquals(messageId, m.getMessageId());
		
		assertEquals(userName, r.getUser().getName());
		assertEquals(topic, r.getTopic());
		assertEquals(content, r.getContent());
		assertEquals(responseId, r.getMessageId());
	}
	
	@Test
	public void testComplexGetMessageByTopic() {
			
		Date d = new Date();
		
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			//nothing to do
		}
		
		String responseContent0_1 = RandomStringUtils.random(25);
		String responseContent0_2 = RandomStringUtils.random(22);
		String responseContent0_3 = RandomStringUtils.random(21);
		Long responseId0_1 = shortMessageService.respondToMessage(userName, responseContent0_1, messageId);
		Long responseId0_2 = shortMessageService.respondToMessage(userName, responseContent0_2, messageId);
		Long responseId0_3 = shortMessageService.respondToMessage(userName, responseContent0_3, messageId);
		
		
		String messageContent1 = RandomStringUtils.random(25);
		String responseContent1_1 = RandomStringUtils.random(25);
		String responseContent1_2 = RandomStringUtils.random(22);
		Long messageId1 = shortMessageService.createMessage(userName, messageContent1, topic);
		Long responseId1_1 = shortMessageService.respondToMessage(userName, responseContent1_1, messageId1);
		Long responseId1_2 = shortMessageService.respondToMessage(userName, responseContent1_2, messageId1);
		
		List<List<Message>> messages = shortMessageService.getMessageByTopic(topic, null);
		
		assertEquals(2, messages.size());	
		assertEquals(4, messages.get(0).size());
		assertEquals(3, messages.get(1).size());
		assertEquals(messageId, messages.get(0).get(0).getMessageId());
		assertEquals(responseId0_1, messages.get(0).get(1).getMessageId());
		assertEquals(responseId0_2, messages.get(0).get(2).getMessageId());
		assertEquals(responseId0_3, messages.get(0).get(3).getMessageId());
		assertEquals(responseContent0_3, messages.get(0).get(3).getContent());
		assertEquals(messageId1, messages.get(1).get(0).getMessageId());
		assertEquals(responseId1_1, messages.get(1).get(1).getMessageId());
		assertEquals(responseId1_2, messages.get(1).get(2).getMessageId());
		
		
		messages = shortMessageService.getMessageByTopic(topic, d);
		assertEquals(1, messages.size());	
		assertEquals(4, messages.get(0).size());
		
	}
	
	
}
