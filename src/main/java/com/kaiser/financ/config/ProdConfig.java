package com.kaiser.financ.config;

import com.kaiser.financ.services.EmailService;
import com.kaiser.financ.services.impl.EmailServiceSmtp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

  @Bean
  public EmailService emailService() {
    return new EmailServiceSmtp();
  }
}
