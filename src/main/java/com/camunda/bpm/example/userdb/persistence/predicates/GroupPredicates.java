package com.camunda.bpm.example.userdb.persistence.predicates;

import com.camunda.bpm.example.userdb.persistence.entity.QGroupEntity;
import com.querydsl.core.types.dsl.BooleanExpression;

public class GroupPredicates {

  private GroupPredicates() {
    throw new AssertionError();
  }

  public static BooleanExpression idEq(String id) {
    QGroupEntity groupEntity = QGroupEntity.groupEntity;
    return groupEntity.id.eq(id);
  }

  public static BooleanExpression idEqAny(String... ids) {
    QGroupEntity groupEntity = QGroupEntity.groupEntity;
    return groupEntity.id.in(ids);
  }

  public static BooleanExpression nameEq(String name) {
    QGroupEntity groupEntity = QGroupEntity.groupEntity;
    return groupEntity.name.eq(name);
  }

  public static BooleanExpression nameLike(String name) {
    QGroupEntity groupEntity = QGroupEntity.groupEntity;
    return groupEntity.name.like(name);
  }

  public static BooleanExpression userIdEq(String userId) {
    QGroupEntity groupEntity = QGroupEntity.groupEntity;
    return groupEntity.users.any().id.eq(userId);
  }

}
