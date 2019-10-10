package com.camunda.bpm.example.userdb.identity.query;

import java.util.List;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.UserQueryImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

import com.camunda.bpm.example.userdb.identity.JpaIdentityProviderSession;

public class JpaUserQueryImpl extends UserQueryImpl {

  private static final long serialVersionUID = 1L;

  public JpaUserQueryImpl() {
    super();
  }

  public JpaUserQueryImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }

  // execute queries /////////////////////////////////////////

  @Override
  public long executeCount(CommandContext commandContext) {
    final JpaIdentityProviderSession provider = getProcessappIdentityProvider(commandContext);
    return provider.findUserCountByQueryCriteria(this);
  }

  @Override
  public List<User> executeList(CommandContext commandContext, Page page) {
    final JpaIdentityProviderSession provider = getProcessappIdentityProvider(commandContext);
    return provider.findUserByQueryCriteria(this);
  }

  @Override
  public UserQuery desc() {
    return super.desc();
  }

  protected JpaIdentityProviderSession getProcessappIdentityProvider(CommandContext commandContext) {
    return (JpaIdentityProviderSession) commandContext.getReadOnlyIdentityProvider();
  }

}
