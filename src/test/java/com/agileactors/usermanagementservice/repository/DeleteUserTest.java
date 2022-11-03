package com.agileactors.usermanagementservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.agileactors.usermanagementservice.model.User;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests {@link com.agileactors.usermanagementservice.model.User users} are properly deleted.
 */
@SpringBootTest
class DeleteUserTest {
  private final UserRepository userRepository;

  @Autowired
  DeleteUserTest(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Tests correct deletion of {@link com.agileactors.usermanagementservice.model.User user}. First
   * creates a user, then deletes and then tries to recover by id.
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
   * Tests correct deletion of all {@link com.agileactors.usermanagementservice.model.User users}.
   * First creates 3 users, then deletes all of them and counts the database.
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

    userRepository.deleteAll();

    assertEquals(0, userRepository.count());
  }
}