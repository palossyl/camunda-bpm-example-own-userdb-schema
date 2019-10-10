package com.camunda.bpm.example.userdb.identity;

import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

import com.camunda.bpm.example.userdb.service.UserIdentityService;

public class JpaIdentityProviderFactory implements SessionFactory {

  protected UserIdentityService userIdentityService;

  public JpaIdentityProviderFactory(UserIdentityService userIdentityService) {
    super();
    this.userIdentityService = userIdentityService;
  }

  @Override
  public Class<?> getSessionType() {
    return ReadOnlyIdentityProvider.class;
  }

  @Override
  public Session openSession() {
    return new JpaIdentityProviderSession(userIdentityService);
  }

}
