package com.camunda.bpm.example.userdb.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.camunda.bpm.example.userdb.persistence.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, String>, QuerydslPredicateExecutor<GroupEntity> {

  Optional<GroupEntity> findById(String id);

}
