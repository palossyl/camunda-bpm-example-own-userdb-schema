package com.camunda.bpm.example.userdb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.camunda.bpm.example.userdb.persistence.entity.UserEntity;
import com.camunda.bpm.example.userdb.persistence.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

  @Autowired
  protected UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserEntity user = userRepository
        .findById(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    return new JpaUserDetails(user);
  }
}
