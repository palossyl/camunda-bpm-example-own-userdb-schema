package com.camunda.bpm.example.userdb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.camunda.bpm.example.userdb.persistence.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class UserdbSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  protected DataSource dataSource;

  @Autowired
  protected UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .headers().frameOptions().sameOrigin()
        .and()
        .authorizeRequests()
        .regexMatchers("/rest/(.*)").authenticated()
        .regexMatchers("/start-process.html").authenticated()
        .and()
        .httpBasic();
  }

}