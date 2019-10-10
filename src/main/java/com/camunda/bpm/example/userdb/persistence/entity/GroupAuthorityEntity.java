package com.camunda.bpm.example.userdb.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "group_authorities")
public class GroupAuthorityEntity {

  @Id
  @GeneratedValue
  protected Long id;

  @ManyToOne
  @JoinColumn(name = "group_id")
  protected GroupEntity group;

  protected String authority;

  public GroupEntity getGroup() {
    return group;
  }

  public void setGroup(GroupEntity group) {
    this.group = group;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

}
