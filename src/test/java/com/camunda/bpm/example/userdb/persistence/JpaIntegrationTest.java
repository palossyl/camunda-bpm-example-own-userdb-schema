package com.camunda.bpm.example.userdb.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.camunda.bpm.example.userdb.persistence.entity.GroupEntity;
import com.camunda.bpm.example.userdb.persistence.entity.UserEntity;
import com.camunda.bpm.example.userdb.persistence.repository.GroupRepository;
import com.camunda.bpm.example.userdb.persistence.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Test
  public void testUserEntity() {
    UserEntity user = userRepository
        .findById("alice")
        .orElseThrow(() -> new RuntimeException("User 'alice' not found"));

    assertNotNull(user);
    assertEquals(user.getEmail(), "alice-the-admin@test.com");
    assertThat(user.getGroups().size(), is(equalTo(1)));
  }

  @Test
  public void testGroupEntity() {
    GroupEntity group = groupRepository
        .findById("admins")
        .orElseThrow(() -> new RuntimeException("Group 'admins' not found"));

    assertNotNull(group);
    assertEquals(group.getName(), "Administrators");
    assertThat(group.getUsers().size(), is(equalTo(1)));
  }
}