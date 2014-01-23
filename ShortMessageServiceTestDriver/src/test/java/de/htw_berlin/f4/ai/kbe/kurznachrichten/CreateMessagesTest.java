package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class CreateMessagesTest extends TestTopicInit{

	 
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMessageTooLong() {
		shortMessageService.createMessage(userName, RandomStringUtils.random(256), topic);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMessageTooShort() {
		shortMessageService.createMessage(userName, RandomStringUtils.random(9), topic);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMessageUserDoesNotExist() {
		//User does not exist
		shortMessageService.createMessage(RandomStringUtils.random(10), RandomStringUtils.random(20), topic);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMessageTopicDoesNotExist() {
		//Topic does not exist
		shortMessageService.createMessage(userName, RandomStringUtils.random(20), RandomStringUtils.random(10));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateMessageUserIsNull() {
		shortMessageService.createMessage(null, RandomStringUtils.random(20), topic);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateMessageTopicIsNull() {
		shortMessageService.createMessage(userName, RandomStringUtils.random(20), null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateMessageMessageIsNull() {
		shortMessageService.createMessage(userName, null, topic);
	}
	
	@Test
	public void testCreateMessage() {
		String content = RandomStringUtils.random(20);
		shortMessageService.createMessage(userName, content, topic);
		
		List<List<Message>> messages = shortMessageService.getMessageByTopic(topic, null);		
		
		assertEquals(1, messages.size());
		assertEquals(1, messages.get(0).size());
		
		Message m = messages.get(0).get(0);
		
		assertEquals(userName, m.getUser().getName());
		assertEquals(content, m.getContent());
		assertEquals(topic, m.getTopic());
	}


}
