package com.camunda.bpm.example.userdb.service.impl;

import static org.camunda.bpm.engine.authorization.Permissions.READ;
import static org.camunda.bpm.engine.authorization.Resources.USER;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.camunda.bpm.engine.authorization.Permission;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.Direction;
import org.camunda.bpm.engine.impl.GroupQueryImpl;
import org.camunda.bpm.engine.impl.GroupQueryProperty;
import org.camunda.bpm.engine.impl.QueryOrderingProperty;
import org.camunda.bpm.engine.impl.UserQueryImpl;
import org.camunda.bpm.engine.impl.UserQueryProperty;
import org.camunda.bpm.engine.impl.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.camunda.bpm.example.userdb.persistence.entity.GroupEntity;
import com.camunda.bpm.example.userdb.persistence.entity.QGroupEntity;
import com.camunda.bpm.example.userdb.persistence.entity.QUserEntity;
import com.camunda.bpm.example.userdb.persistence.entity.UserEntity;
import com.camunda.bpm.example.userdb.persistence.predicates.GroupPredicates;
import com.camunda.bpm.example.userdb.persistence.predicates.UserPredicates;
import com.camunda.bpm.example.userdb.persistence.repository.GroupRepository;
import com.camunda.bpm.example.userdb.persistence.repository.UserRepository;
import com.camunda.bpm.example.userdb.service.UserIdentityService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;

@Component("userIdentityService")
public class UserIdentityServiceImpl implements UserIdentityService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserIdentityServiceImpl.class);

  private static final String GROUPTYPE_SYTEM = "SYSTEM";

  @Autowired
  UserRepository userRepository;

  @Autowired
  GroupRepository groupRepository;

  // users ////////////////////////////

  @Override
  public List<User> findUsers(UserQueryImpl userQueryImpl) {
    List<UserEntity> userEntities;

    BooleanBuilder where = buildUsersWhereCondition(userQueryImpl);
    List<OrderSpecifier<?>> orderbys = buildUsersOrderbyClause(userQueryImpl);

    if (orderbys.isEmpty()) {
      userEntities = getListFromIteralbe(userRepository.findAll(where));
    } else {
      userEntities = getListFromIteralbe(userRepository.findAll(where, orderbys.toArray(new OrderSpecifier[0])));
    }

    List<User> users = new ArrayList<>();
    for (UserEntity userEntity : userEntities) {
      User user = transformUser(userEntity);
      if (isAuthenticatedUser(user) || isAuthorized(READ, USER, user.getId())) {
        users.add(user);
      }
    }

    return users;
  }

  protected BooleanBuilder buildUsersWhereCondition(UserQueryImpl query) {
    BooleanBuilder where = new BooleanBuilder();

    if (query != null) {
      if (query.getId() != null) {
        where.and(UserPredicates.idEq(query.getId()));
      }
      if (query.getIds() != null && query.getIds().length > 0) {
        where.and(UserPredicates.idEqAny(query.getIds()));
      }
      if (query.getEmail() != null) {
        where.and(UserPredicates.emailEq(query.getEmail()));
      }
      if (query.getEmailLike() != null) {
        where.and(UserPredicates.emailLike(query.getEmailLike()));
      }
      if (query.getFirstName() != null) {
        where.and(UserPredicates.firstnameEq(query.getFirstName()));
      }
      if (query.getFirstNameLike() != null) {
        where.and(UserPredicates.firstnameLike(query.getFirstNameLike()));
      }
      if (query.getLastName() != null) {
        where.and(UserPredicates.lastnameEq(query.getLastName()));
      }
      if (query.getLastNameLike() != null) {
        where.and(UserPredicates.lastnameLike(query.getLastNameLike()));
      }
      if (query.getGroupId() != null) {
        where.and(UserPredicates.groupIdEq(query.getGroupId()));
      }
    }

    return where;
  }

  protected List<OrderSpecifier<?>> buildUsersOrderbyClause(UserQueryImpl query) {
    List<QueryOrderingProperty> orderingProperties = query.getOrderingProperties();

    List<OrderSpecifier<?>> orderbys = new ArrayList<OrderSpecifier<?>>();

    if (orderingProperties != null && !orderingProperties.isEmpty()) {
      for (QueryOrderingProperty orderingProperty : orderingProperties) {
        if (orderingProperty.getQueryProperty().equals(UserQueryProperty.USER_ID)) {
          if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
            orderbys.add(QUserEntity.userEntity.id.asc());
          } else {
            orderbys.add(QUserEntity.userEntity.id.desc());
          }
        } else if (orderingProperty.getQueryProperty().equals(UserQueryProperty.FIRST_NAME)) {
          if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
            orderbys.add(QUserEntity.userEntity.firstname.asc());
          } else {
            orderbys.add(QUserEntity.userEntity.firstname.desc());
          }
        } else if (orderingProperty.getQueryProperty().equals(UserQueryProperty.LAST_NAME)) {
          if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
            orderbys.add(QUserEntity.userEntity.lastname.asc());
          } else {
            orderbys.add(QUserEntity.userEntity.lastname.desc());
          }
        } else if (orderingProperty.getQueryProperty().equals(UserQueryProperty.EMAIL)) {
          if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
            orderbys.add(QUserEntity.userEntity.email.asc());
          } else {
            orderbys.add(QUserEntity.userEntity.email.desc());
          }
        }
      }
    }

    return orderbys;
  }

  protected User transformUser(UserEntity userEntity) {
    org.camunda.bpm.engine.impl.persistence.entity.UserEntity user = new org.camunda.bpm.engine.impl.persistence.entity.UserEntity();

    user.setId(userEntity.getId());
    user.setEmail(userEntity.getEmail());
    user.setFirstName(userEntity.getFirstname());
    user.setLastName(userEntity.getLastname());
    user.setPassword(userEntity.getPassword());
    user.setDbPassword(userEntity.getPassword());
    user.setRevision(1);

    return (User) user;
  }

  // groups ////////////////////////////

  @Override
  public List<Group> findGroups(GroupQueryImpl groupQueryImpl) {
    List<GroupEntity> groupEntities;

    BooleanBuilder where = buildGroupsWhereCondition(groupQueryImpl);
    List<OrderSpecifier<?>> orderbys = buildGroupsOrderbyClause(groupQueryImpl);

    if (orderbys.isEmpty()) {
      groupEntities = getListFromIteralbe(groupRepository.findAll(where));
    } else {
      groupEntities = getListFromIteralbe(groupRepository.findAll(where, orderbys.toArray(new OrderSpecifier[0])));
    }

    List<Group> groups = new ArrayList<Group>();
    for (GroupEntity groupEntity : groupEntities) {
      groups.add(transformGroup(groupEntity));
    }

    return groups;
  }

  protected BooleanBuilder buildGroupsWhereCondition(GroupQueryImpl query) {
    BooleanBuilder where = new BooleanBuilder();

    if (query != null) {
      if (query.getId() != null) {
        where.and(GroupPredicates.idEq(query.getId()));
      }
      if (query.getIds() != null && query.getIds().length > 0) {
        where.and(GroupPredicates.idEqAny(query.getId()));
      }
      if (query.getName() != null) {
        where.and(GroupPredicates.nameEq(query.getName()));
      }
      if (query.getNameLike() != null) {
        where.and(GroupPredicates.nameLike(query.getNameLike()));
      }
      if (query.getUserId() != null) {
        where.and(GroupPredicates.userIdEq(query.getUserId()));
      }
    }

    return where;
  }

  protected List<OrderSpecifier<?>> buildGroupsOrderbyClause(GroupQueryImpl query) {
    List<OrderSpecifier<?>> orderbys = new ArrayList<OrderSpecifier<?>>();

    List<QueryOrderingProperty> orderingProperties = query.getOrderingProperties();

    if (orderingProperties != null && !orderingProperties.isEmpty()) {
      for (QueryOrderingProperty orderingProperty : orderingProperties) {
        if (orderingProperty.getQueryProperty().equals(GroupQueryProperty.GROUP_ID)) {
          if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
            orderbys.add(QGroupEntity.groupEntity.id.asc());
          } else {
            orderbys.add(QGroupEntity.groupEntity.id.desc());
          }
        } else if (orderingProperty.getQueryProperty().equals(GroupQueryProperty.NAME)) {
          if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
            orderbys.add(QGroupEntity.groupEntity.name.asc());
          } else {
            orderbys.add(QGroupEntity.groupEntity.name.desc());
          }
        }
      }
    }

    return orderbys;
  }

  protected Group transformGroup(GroupEntity groupEntity) {
    Group group = new org.camunda.bpm.engine.impl.persistence.entity.GroupEntity();

    group.setId(groupEntity.getId());
    group.setName(groupEntity.getName());
    group.setType(GROUPTYPE_SYTEM);

    return group;
  }

  // password ////////////////////////////

  @Override
  public boolean checkPassword(String username, String password) {
    UserEntity user = userRepository
        .findById(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    return password.equals(user.getPassword());
  }

  // Utils ////////////////////////////////////////////

  protected boolean isAuthenticatedUser(User user) {
    if (user.getId() == null) {
      return false;
    }
    return user.getId()
        .equals(Context.getCommandContext().getAuthenticatedUserId());
  }

  protected boolean isAuthorized(Permission permission, Resource resource, String resourceId) {
    return Context.getCommandContext().getAuthorizationManager()
        .isAuthorized(permission, resource, resourceId);
  }

  protected static <T> List<T> getListFromIteralbe(Iterable<T> itr) {
    return (List<T>) StreamSupport.stream(itr.spliterator(), false).collect(Collectors.toList());
  }
}
