package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class DeleteMessagesTest extends TestMessageInit{

	@Test(expected=AuthorizationException.class)
	public void testUserNotAuthorized() throws AuthorizationException {
		shortMessageService.deleteMessage(userName2, messageId);	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteMessageDoesNotExist() throws AuthorizationException {
		shortMessageService.deleteMessage(userName, 937372L);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteMessageIsResponse() throws AuthorizationException {
		Long responseMessageId = shortMessageService.respondToMessage(userName, RandomStringUtils.random(20), messageId);
		shortMessageService.deleteMessage(userName, responseMessageId);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteMessageUserDoesNotExist() throws AuthorizationException {
		shortMessageService.deleteMessage(RandomStringUtils.random(10), messageId);
	}
	
	@Test(expected=NullPointerException.class)
	public void testDeleteMessageIsNull() throws AuthorizationException {
		shortMessageService.deleteMessage(userName, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testDeleteMessageUserIsNull() throws AuthorizationException {
		shortMessageService.deleteMessage(null, messageId);
	}
	
	@Test(expected=AuthorizationException.class)
	public void testDeleteMessageIsDifferentUser() throws AuthorizationException {
		shortMessageService.deleteMessage(userName2, messageId);
	}
	
	@Test
	public void testDeleteMessage() throws AuthorizationException {
		shortMessageService.deleteMessage(userName, messageId);		
		List<List<Message>> messages = shortMessageService.getMessageByTopic(topic, null);
		assertTrue(messages.isEmpty());				
	}
	
}
