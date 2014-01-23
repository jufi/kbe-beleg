package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;


public class TopicTest extends TestUserInit{

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTopicUserDoesNotExist() {
		//User does not exist
		shortMessageService.createTopic(RandomStringUtils.random(10), topic);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateSameTopic() {
		//Topic can already exist
		try {
			shortMessageService.createTopic(userName, topic);
		} catch (IllegalArgumentException e) {
			
		}
		shortMessageService.createTopic(userName, topic);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTopicNameTooLong() {
		shortMessageService.createTopic(userName, RandomStringUtils.random(71));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTopicNameTooShort() {
		shortMessageService.createTopic(userName, RandomStringUtils.random(1));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateTopicUserIsNull() {
		shortMessageService.createTopic(null , RandomStringUtils.random(10));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateTopicNameIsNull() {
		shortMessageService.createTopic(userName, null);
	}
	
	@Test
	public void testCreateTopic() {
		
		int nbOfTopics = shortMessageService.getTopics().size();
		String validTopic = RandomStringUtils.random(20);	
		shortMessageService.createTopic(userName, validTopic);
		Set<String> topics = shortMessageService.getTopics();
		assertEquals(nbOfTopics+1, topics.size());
		assertTrue(topics.contains(validTopic));
		
	}	 	
	 
	
}
