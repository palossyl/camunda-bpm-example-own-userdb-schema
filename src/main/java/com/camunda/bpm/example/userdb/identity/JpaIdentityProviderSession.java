package com.camunda.bpm.example.userdb.identity;

import java.util.List;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.NativeUserQuery;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.TenantQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.camunda.bpm.example.userdb.identity.query.JpaGroupQueryImpl;
import com.camunda.bpm.example.userdb.identity.query.JpaTenantQueryImpl;
import com.camunda.bpm.example.userdb.identity.query.JpaUserQueryImpl;
import com.camunda.bpm.example.userdb.service.UserIdentityService;

public class JpaIdentityProviderSession implements ReadOnlyIdentityProvider {

  protected Logger LOGGER = LoggerFactory.getLogger(JpaIdentityProviderSession.class);

  protected UserIdentityService userIdentityService;

  public JpaIdentityProviderSession(UserIdentityService userIdentityService) {
    super();
    this.userIdentityService = userIdentityService;
  }

  // Session Lifecycle //////////////////////////////////

  @Override
  public void flush() {
    // nothing to do
  }

  @Override
  public void close() {
    // nothing to do
  }

  // Users /////////////////////////////////////////////////

  @Override
  public User findUserById(String userId) {
    return createUserQuery(org.camunda.bpm.engine.impl.context.Context.getCommandContext())
        .userId(userId)
        .singleResult();
  }

  @Override
  public UserQuery createUserQuery() {
    return new JpaUserQueryImpl(org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration()
        .getCommandExecutorTxRequired());
  }

  @Override
  public UserQuery createUserQuery(CommandContext commandContext) {
    return new JpaUserQueryImpl();
  }

  @Override
  public NativeUserQuery createNativeUserQuery() {
    throw new BadUserRequestException(
        "Native user queries are not supported for the JPA identity service provider.");
  }

  public long findUserCountByQueryCriteria(JpaUserQueryImpl query) {
    return userIdentityService.findUsers(query).size();
  }

  public List<User> findUserByQueryCriteria(JpaUserQueryImpl query) {
    return userIdentityService.findUsers(query);
  }

  @Override
  public boolean checkPassword(String userId, String password) {

    // prevent a null password
    if (password == null) {
      return false;
    }

    // engine can't work without users
    if (userId == null || userId.isEmpty()) {
      return false;
    }

    boolean isOk = userIdentityService.checkPassword(userId, password);

    LOGGER.debug(String.format("Checking password for %s : %b", userId, isOk));

    return isOk;
  }

  // Groups ///////////////////////////////////////////////

  @Override
  public Group findGroupById(String groupId) {
    return createGroupQuery(org.camunda.bpm.engine.impl.context.Context.getCommandContext())
        .groupId(groupId)
        .singleResult();
  }

  @Override
  public GroupQuery createGroupQuery() {
    return new JpaGroupQueryImpl(org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration()
        .getCommandExecutorTxRequired());
  }

  @Override
  public GroupQuery createGroupQuery(CommandContext commandContext) {
    return new JpaGroupQueryImpl();
  }

  public long findGroupCountByQueryCriteria(JpaGroupQueryImpl query) {
    return userIdentityService.findGroups(query).size();
  }

  public List<Group> findGroupByQueryCriteria(JpaGroupQueryImpl query) {
    return userIdentityService.findGroups(query);
  }

  // Tenants ////////////////////////////////////////////

  @Override
  public TenantQuery createTenantQuery() {
    return new JpaTenantQueryImpl(org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration()
        .getCommandExecutorTxRequired());
  }

  @Override
  public TenantQuery createTenantQuery(CommandContext commandContext) {
    return new JpaTenantQueryImpl();
  }

  @Override
  public Tenant findTenantById(String id) {
    // since multi-tenancy is not supported for the JPA plugin, always return
    // null
    return null;
  }

}
