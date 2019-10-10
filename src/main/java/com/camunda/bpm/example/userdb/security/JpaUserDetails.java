package com.camunda.bpm.example.userdb.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.camunda.bpm.example.userdb.persistence.entity.GroupAuthorityEntity;
import com.camunda.bpm.example.userdb.persistence.entity.GroupEntity;
import com.camunda.bpm.example.userdb.persistence.entity.UserEntity;

public class JpaUserDetails implements UserDetails {

  private static final long serialVersionUID = 1L;

  protected String password;
  protected String username;
  protected List<GrantedAuthority> grantedAuthorities = new ArrayList();

  public JpaUserDetails(UserEntity user) {
    this.username = user.getId();
    this.password = user.getPassword();
    Set<GroupEntity> groups = user.getGroups();
    for (GroupEntity group : groups) {
      Set<GroupAuthorityEntity> groupAuthorities = group.getGroupAuthorities();
      for (GroupAuthorityEntity groupAuthority : groupAuthorities) {
        grantedAuthorities.add(new SimpleGrantedAuthority(groupAuthority.getAuthority()));
      }
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
