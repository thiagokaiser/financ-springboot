package com.kaiser.financ.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.kaiser.financ.services.EmailService;
import com.kaiser.financ.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {	
	
	@Bean
	public EmailService emailService() {		
		return new SmtpEmailService();
	}

}
