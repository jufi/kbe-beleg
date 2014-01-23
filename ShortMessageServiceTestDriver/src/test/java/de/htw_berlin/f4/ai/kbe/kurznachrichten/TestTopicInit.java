package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class TestTopicInit extends TestUserInit {

	
	
	@Before
	public void setUp() {
		super.setUp();
		try {
			shortMessageService.createTopic(userName, topic);
		} catch (IllegalArgumentException e) {
			// nothing to do
		}
	}

	
	
	@After
	public void tearDown() {
		Set<String> topics = shortMessageService.getTopics();
		for(String topic: topics){
			List<List<Message>> messages = shortMessageService.getMessageByTopic(topic, null);
			for(List<Message> m: messages){
				try {
					shortMessageService.deleteMessage(m.get(0).getUser().getName(), m.get(0).getMessageId());
				} catch (AuthorizationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		super.tearDown();
	}

	@Ignore
	@Test
	public void dummy(){
		
	}
}
