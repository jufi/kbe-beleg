package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import de.htw_berlin.f4.ai.kbe.appconfig.AppConfig;

/**
 * @author Kevin Goy
 */
public class ShortMessageServiceIntegrationTest {

	static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	static final ShortMessageService shortMessageService = (ShortMessageService) context.getBean("shortMessageService");

	@Before
	public void prepareEnvironment() {
		Set<User> userList = shortMessageService.getUsers();
	}

	@AfterClass
	public static void shutdownEnvironment() {
		((AbstractApplicationContext) context).registerShutdownHook();
	}

	@Test
	public void testMethod() {

	}

}
