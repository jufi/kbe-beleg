package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class TestUserInit extends TestInit{

	protected Long adminId;
	protected Long userId;
	
	@Before
	public void setUp(){
		shortMessageService.createUser(userName, city);
		shortMessageService.createUser(userName2, city);
	}
	 
	@After 
	public void tearDown(){
		Set<User> users = shortMessageService.getUsers();
		for(User u: users){
			shortMessageService.deleteUser(u.getName());
		}
	}
	
	@Ignore
	@Test
	public void dummy(){
		
	}
}
