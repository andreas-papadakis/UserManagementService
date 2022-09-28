package com.agileactors.usermanagementservice.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test users are properly deleted.
 */
@SpringBootTest
class DeleteUserRepositoryTest {
  private final UserRepository userRepository;

  @Autowired
  DeleteUserRepositoryTest(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Test correct deletion of user. First create a user, then delete and then try to recover by id.
   */
  @Test
  public void testDeleteUser() {
    User testUser = new User(UUID.randomUUID(),
                             "testFName",
                             "testLName",
                             "a@a.com",
                             null,
                             null);

    userRepository.save(testUser);
    userRepository.deleteById(testUser.getId());

    assertFalse(userRepository.findById(testUser.getId()).isPresent());
  }

  /**
   * Test correct deletion of all users.
   * First create 3 users, then delete all and count the database.
   */
  @Test
  public void testDeleteAllUsers() {
    User testUser1 = new User(UUID.randomUUID(),
                              "testFName",
                              "testLName",
                              "a@a.com",
                              null,
                              null);
    User testUser2 = new User(UUID.randomUUID(),
                              "testFName",
                              "testLName",
                              "a@a.com",
                              null,
                              null);
    User testUser3 = new User(UUID.randomUUID(),
                              "testFName",
                              "testLName",
                              "a@a.com",
                              null,
                              null);

    userRepository.save(testUser1);
    userRepository.save(testUser2);
    userRepository.save(testUser3);

    userRepository.deleteAllUsers();

    assertEquals(0, userRepository.count());
  }
}