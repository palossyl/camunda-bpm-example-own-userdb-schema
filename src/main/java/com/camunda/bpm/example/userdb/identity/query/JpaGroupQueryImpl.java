package com.camunda.bpm.example.userdb.identity.query;

import java.util.List;

import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.impl.GroupQueryImpl;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

import com.camunda.bpm.example.userdb.identity.JpaIdentityProviderSession;

public class JpaGroupQueryImpl extends GroupQueryImpl {

  private static final long serialVersionUID = 1L;

  public JpaGroupQueryImpl() {
    super();
  }

  public JpaGroupQueryImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }

  // execute queries ////////////////////////////

  @Override
  public long executeCount(CommandContext commandContext) {
    final JpaIdentityProviderSession provider = getProcessappIdentityProvider(commandContext);
    return provider.findGroupCountByQueryCriteria(this);
  }

  @Override
  public List<Group> executeList(CommandContext commandContext, Page page) {
    final JpaIdentityProviderSession provider = getProcessappIdentityProvider(commandContext);
    return provider.findGroupByQueryCriteria(this);
  }

  @Override
  public GroupQuery desc() {
    return super.desc();
  }

  protected JpaIdentityProviderSession getProcessappIdentityProvider(CommandContext commandContext) {
    return (JpaIdentityProviderSession) commandContext.getReadOnlyIdentityProvider();
  }

}
