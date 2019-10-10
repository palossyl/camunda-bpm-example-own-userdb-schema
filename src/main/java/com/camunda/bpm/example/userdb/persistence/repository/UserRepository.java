package com.camunda.bpm.example.userdb.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.camunda.bpm.example.userdb.persistence.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>, QuerydslPredicateExecutor<UserEntity> {

  Optional<UserEntity> findById(String id);

}
