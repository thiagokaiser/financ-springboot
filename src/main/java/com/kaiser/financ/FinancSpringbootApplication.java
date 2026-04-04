package com.kaiser.financ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinancSpringbootApplication {

  public static void main(String[] args) {
    SpringApplication.run(FinancSpringbootApplication.class, args);
  }
}
