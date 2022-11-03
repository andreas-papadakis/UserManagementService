package com.agileactors.usermanagementservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.agileactors.usermanagementservice.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests correct retrieve of {@link com.agileactors.usermanagementservice.model.User user(s)}.
 */
@SpringBootTest
class GetUserTest {
  private final UserRepository userRepository;

  @Autowired
  GetUserTest(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Test correct read of {@link com.agileactors.usermanagementservice.model.User users}. First
   * creates two users, then tries to recover them. Check for at least 2 recovered users, just in
   * case some already existed in DB.
   */
  @Test
  public void testGetAllUsers() {
    userRepository.deleteAll();

    User testUser1 = new User(UUID.randomUUID(),
                              "testFName",
                              "testLName",
                              "a@a.com",
                              null,
                              null);
    User testUser2 = new User(UUID.randomUUID(),
                              "testFirstName",
                              "testLastName",
                              "b@b.com",
                              null,
                              null);

    userRepository.save(testUser1);
    userRepository.save(testUser2);

    List<User> retrievedUsers = userRepository.findAll();

    assertEquals(2, retrievedUsers.size());
  }

  /**
   * Tests correct read of specific a {@link com.agileactors.usermanagementservice.model.User user}.
   * First creates a user, then tries to recover by id.
   */
  @Test
  public void testGetUserById() {
    User testUser1 = new User(UUID.randomUUID(),
                              "testFName",
                              "testLName",
                              "a@a.com",
                              null,
                              null);

    userRepository.save(testUser1);
    Optional<User> retrievedUser = userRepository.findById(testUser1.getId());

    assertTrue(retrievedUser.isPresent());
    assertNotNull(userRepository.findById(testUser1.getId()).get());
  }

  /**
   * Tests correct read of all {@link com.agileactors.usermanagementservice.model.User users}
   * filtered by first name. First creates two users with similar first names, then tries to recover
   * them.
   */
  @Test
  public void testGetAllUserFilteredByFirstName() {
    userRepository.deleteAll();

    User testUser1 = new User(UUID.randomUUID(),
                              "testFName",
                              "testLName",
                              "a@a.com",
                              null,
                              null);
    User testUser2 = new User(UUID.randomUUID(),
                              "abctestFNameasdasdasdasdasd",
                              "testLName",
                              "a@a.com",
                              null,
                              null);

    userRepository.save(testUser1);
    userRepository.save(testUser2);

    List<User> retrievedUsers =
            userRepository.findByFirstNameLike("%" + testUser1.getFirstName() + "%");

    assertEquals(2, retrievedUsers.size());
  }

  /**
   * Tests correct read of all {@link com.agileactors.usermanagementservice.model.User users}
   * filtered by last name. First creates two users with similar last names, then tries to recover
   * them.
   */
  @Test
  public void testGetAllUserFilteredByLastName() {
    userRepository.deleteAll();

    User testUser1 = new User(UUID.randomUUID(),
            "testFName",
            "testLName",
            "a@a.com",
            null,
            null);
    User testUser2 = new User(UUID.randomUUID(),
            "testFName",
            "sadfasdftestLNameasdfasdf",
            "a@a.com",
            null,
            null);

    userRepository.save(testUser1);
    userRepository.save(testUser2);

    List<User> retrievedUsers =
            userRepository.findByLastNameLike("%" + testUser1.getLastName() + "%");

    assertEquals(2, retrievedUsers.size());
  }

  /**
   * Tests correct read of all {@link com.agileactors.usermanagementservice.model.User users}
   * filtered by first and last name. First creates two users with similar first and last names,
   * then tries to recover them.
   */
  @Test
  public void testGetAllUserFilteredByFirstAndLastName() {
    userRepository.deleteAll();

    User testUser1 = new User(UUID.randomUUID(),
            "testFName",
            "testLName",
            "a@a.com",
            null,
            null);
    User testUser2 = new User(UUID.randomUUID(),
            "testFName",
            "sadfasdftestLNameasdfasdf",
            "a@a.com",
            null,
            null);

    userRepository.save(testUser1);
    userRepository.save(testUser2);

    List<User> retrievedUsers =
            userRepository.findByFirstNameLikeAndLastNameLike("%" + testUser1.getFirstName() + "%",
                                                              "%" + testUser1.getLastName() + "%");

    assertEquals(2, retrievedUsers.size());
  }
}