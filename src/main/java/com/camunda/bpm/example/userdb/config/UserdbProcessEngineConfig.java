package com.camunda.bpm.example.userdb.config;

import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.camunda.bpm.example.userdb.identity.plugin.JpaIdentityProviderPlugin;
import com.camunda.bpm.example.userdb.service.UserIdentityService;

@Configuration
public class UserdbProcessEngineConfig {

  @Bean
  public ProcessEnginePlugin administratorAuthorizationPlugin() {
    AdministratorAuthorizationPlugin administratorAuthorizationPlugin = new AdministratorAuthorizationPlugin();
    administratorAuthorizationPlugin.setAdministratorGroupName("admins");
    return administratorAuthorizationPlugin;
  }

  @Bean
  public ProcessEnginePlugin identityProviderPlugin(UserIdentityService userIdentityService) {
    return new JpaIdentityProviderPlugin(userIdentityService);
  }

}
