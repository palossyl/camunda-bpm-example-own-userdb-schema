package com.camunda.bpm.example.userdb.persistence.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class GroupEntity {

  @Id
  protected String id;

  protected String name;

  @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
  protected Set<GroupAuthorityEntity> groupAuthorities;

  @ManyToMany(mappedBy = "groups")
  protected Set<UserEntity> users;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<GroupAuthorityEntity> getGroupAuthorities() {
    return groupAuthorities;
  }

  public void setGroupAuthorities(Set<GroupAuthorityEntity> groupAuthorities) {
    this.groupAuthorities = groupAuthorities;
  }

  public Set<UserEntity> getUsers() {
    return users;
  }

  public void setUsers(Set<UserEntity> users) {
    this.users = users;
  }

}