package com.camunda.bpm.example.userdb.identity.plugin;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.camunda.bpm.example.userdb.identity.JpaIdentityProviderFactory;
import com.camunda.bpm.example.userdb.service.UserIdentityService;

public class JpaIdentityProviderPlugin implements ProcessEnginePlugin {

  protected Logger LOGGER = LoggerFactory.getLogger(JpaIdentityProviderPlugin.class);

  protected UserIdentityService userIdentityService;

  public JpaIdentityProviderPlugin(UserIdentityService userIdentityService) {
    super();
    this.userIdentityService = userIdentityService;
  }

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    LOGGER.info(String.format("PLUGIN %s activated on process engine %s",
        getClass().getSimpleName(), processEngineConfiguration.getProcessEngineName()));

    processEngineConfiguration
        .setIdentityProviderSessionFactory(new JpaIdentityProviderFactory(userIdentityService));
  }

  @Override
  public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    // nothing to do
  }

  @Override
  public void postProcessEngineBuild(ProcessEngine processEngine) {
    // nothing to do
  }

}
