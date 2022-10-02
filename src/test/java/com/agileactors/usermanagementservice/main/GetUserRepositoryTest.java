package com.agileactors.usermanagementservice.main;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests correct retrieve of {@link com.agileactors.usermanagementservice.model.User user(s)}.
 */
@SpringBootTest
class GetUserRepositoryTest {
  private final UserRepository userRepository;

  @Autowired
  GetUserRepositoryTest(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Test correct read of {@link com.agileactors.usermanagementservice.model.User users}. First
   * creates two users, then tries to recover them. Check for at least 2 recovered users, just in
   * case some already existed in DB.
   */
  @Test
  public void testGetAllUsers() {
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

    assertTrue(retrievedUsers.size() >= 2);
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

    assertNotNull(userRepository.findById(testUser1.getId()).get()); //TODO: correct the assertion
  }

  /**
   * Tests correct read of all {@link com.agileactors.usermanagementservice.model.User users}
   * filtered by first name. First creates two users with similar first names, then tries to recover
   * them.
   */
  @Test
  public void testGetAllUserFilteredByFirstName() {
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

    List<User> retrievedUsers = userRepository.findByFirstNameLike(testUser1.getFirstName());

    assertTrue(retrievedUsers.size() >= 2); //TODO: clear the DB before test runs and check size is exactly equal to 2 and not also greater than
  }

  /**
   * Tests correct read of all {@link com.agileactors.usermanagementservice.model.User users}
   * filtered by last name. First creates two users with similar last names, then tries to recover
   * them.
   */
  @Test
  public void testGetAllUserFilteredByLastName() {
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

    List<User> retrievedUsers = userRepository.findByLastNameLike(testUser1.getLastName());

    assertTrue(retrievedUsers.size() >= 2); //TODO: clear the DB before test runs and check size is exactly equal to 2 and not also greater than
  }

  /**
   * Tests correct read of all {@link com.agileactors.usermanagementservice.model.User users}
   * filtered by first and last name. First creates two users with similar first and last names,
   * then tries to recover them.
   */
  @Test
  public void testGetAllUserFilteredByFirstAndLastName() {
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

    List<User> retrievedUsers = userRepository.findByLastNameLike(testUser1.getLastName());

    assertTrue(retrievedUsers.size() >= 2); //TODO: clear the DB before test runs and check size is exactly equal to 2 and not also greater than
  }
}