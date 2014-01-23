package de.htw_berlin.f4.ai.kbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.htw_berlin.f4.ai.kbe.appconfig.AppConfig;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.ShortMessageService;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.ShortMessageServiceImpl;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;

@Configuration
@Import(AppConfig.class) 
public class SpringConfig {

	@Bean
	ShortMessageService shortMessageService(){
		return new ShortMessageServiceImpl();
	}

	@Bean
	AppConfig appConfig() {
		return new AppConfig();
	}

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator(){
        return new HibernateExceptionTranslator();
    }
	
}
 