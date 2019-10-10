package com.camunda.bpm.example.userdb.persistence.predicates;

import com.camunda.bpm.example.userdb.persistence.entity.QUserEntity;
import com.querydsl.core.types.dsl.BooleanExpression;

public class UserPredicates {

  private UserPredicates() {
    throw new AssertionError();
  }

  public static BooleanExpression idEq(String id) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.id.eq(id);
  }

  public static BooleanExpression idEqAny(String... ids) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.id.in(ids);
  }

  public static BooleanExpression lastnameEq(String lastname) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.lastname.eq(lastname);
  }

  public static BooleanExpression lastnameLike(String lastname) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.lastname.like(lastname);
  }

  public static BooleanExpression firstnameEq(String firstname) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.firstname.eq(firstname);
  }

  public static BooleanExpression firstnameLike(String firstname) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.firstname.like(firstname);
  }

  public static BooleanExpression emailEq(String email) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.email.eq(email);
  }

  public static BooleanExpression emailLike(String email) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.email.like(email);
  }

  public static BooleanExpression groupIdEq(String groupId) {
    QUserEntity userEntity = QUserEntity.userEntity;
    return userEntity.groups.any().id.eq(groupId);
  }

}
