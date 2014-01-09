package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import de.htw_berlin.f4.ai.kbe.kurznachrichten.ShortMessageService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
   classes =  de.htw_berlin.f4.ai.kbe.config.SpringConfig.class)
public class TestInit {

	@Autowired
	protected ShortMessageService shortMessageService; 

	@Ignore
	@Test
	public void dummy(){
		
	}
	
}