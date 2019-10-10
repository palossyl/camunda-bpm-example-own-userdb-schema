package com.camunda.bpm.example.userdb.service;

import java.util.List;

import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.GroupQueryImpl;
import org.camunda.bpm.engine.impl.UserQueryImpl;

public interface UserIdentityService {

  public List<User> findUsers(UserQueryImpl userQueryImpl);

  public List<Group> findGroups(GroupQueryImpl groupQueryImpl);

  public boolean checkPassword(String userId, String password);

}
