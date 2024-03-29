package com.kaiser.financ.configs;

import com.kaiser.financ.services.DBService;
import com.kaiser.financ.services.EmailService;
import com.kaiser.financ.services.impl.EmailServiceMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

  @Autowired private DBService dbService;

  @Bean
  public boolean instantiateDatabase() {
    dbService.instantiateTestDatabase();
    return true;
  }

  @Bean
  public EmailService emailService() {
    return new EmailServiceMock();
  }
}
