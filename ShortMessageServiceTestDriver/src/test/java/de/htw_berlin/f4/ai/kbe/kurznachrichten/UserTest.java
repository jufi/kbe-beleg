package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import static org.junit.Assert.*;

import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

public class UserTest extends TestInit{

	 
	@After
	public void tearDown(){
		for(User user : shortMessageService.getUsers()){
			shortMessageService.deleteUser(user.getName());
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateUserExists () {
		shortMessageService.createUser(userName, city);
		shortMessageService.createUser(userName, RandomStringUtils.random(10));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateUserNameTooLong() {
		shortMessageService.createUser(RandomStringUtils.random(31), RandomStringUtils.random(10));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateUserNameTooShort() {
		shortMessageService.createUser(RandomStringUtils.random(3), RandomStringUtils.random(10));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateUserNameIsNull() {
		shortMessageService.createUser(null, RandomStringUtils.random(10));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateUserCityIsNull() {
		shortMessageService.createUser(RandomStringUtils.random(10), null);
	}
	
	@Test
	public void testCreateUser() {
		shortMessageService.createUser(userName, city);
		Set<User> users = shortMessageService.getUsers();		
		assertEquals(1, users.size());		
		User user = users.iterator().next();
		assertEquals(userName, user.getName());
		assertEquals(city, user.getCity());
	}
	
	@Test
	public void testDeleteUser() {
		shortMessageService.createUser(userName, city);
		assertEquals(1, shortMessageService.getUsers().size());	
		shortMessageService.deleteUser(userName);
		assertTrue(shortMessageService.getUsers().isEmpty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteUserDoesntExist() {
		shortMessageService.deleteUser(userName);
	}
	
	
}
