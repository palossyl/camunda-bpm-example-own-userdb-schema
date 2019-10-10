package com.camunda.bpm.example.userdb.identity.query;

import java.util.Collections;
import java.util.List;

import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.TenantQueryImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

public class JpaTenantQueryImpl extends TenantQueryImpl {

  private static final long serialVersionUID = 1L;

  public JpaTenantQueryImpl() {
    super();
  }

  public JpaTenantQueryImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }

  // execute queries ////////////////////////////

  @Override
  public long executeCount(CommandContext commandContext) {
    return 0;
  }

  @Override
  public List<Tenant> executeList(CommandContext commandContext, Page page) {
    return Collections.emptyList();
  }

}
