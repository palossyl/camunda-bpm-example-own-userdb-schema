package com.camunda.bpm.example.userdb.delegate;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelegateBeans {

  @Bean
  public JavaDelegate helloDelegate() {
    return execution -> System.out.println("\n\nHello from 'SimpleProcess'!");
  }

}