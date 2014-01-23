package de.htw_berlin.f4.ai.kbe.kurznachrichten;


import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class TestMessageInit extends TestTopicInit{

	Long messageId;
	String messageContent;
	@Before
	public void setUp() {
		super.setUp();
		messageContent = RandomStringUtils.random(20);
		messageId = shortMessageService.createMessage(userName, messageContent, topic);
	}
	
	@After
	public void tearDown() {
		
		super.tearDown();
	}

	@Ignore
	@Test
	public void dummy(){
		
	}
}
