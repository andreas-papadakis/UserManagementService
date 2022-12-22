package com.agileactors.usermanagementservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.agileactors.usermanagementservice.model.User;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests correct update of {@link com.agileactors.usermanagementservice.model.User user}.
 */
@SpringBootTest
class UpdateUserTest {
  private final UserRepository userRepository;

  @Autowired
  UpdateUserTest(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Test correct update of specific {@link com.agileactors.usermanagementservice.model.User user}.
   * First creates a user, then updates all fields except ID and retrieves again.
   * Compares retrieved values with expected.
   * No check for validations as already checked in
   * {@link PostUserTest
   * CreateUserRepositoryTest}.
   */
  @Test
  public void testUpdateUser() {
    User testUser = new User(UUID.randomUUID(),
                             "testFName",
                             "testLName",
                             "a@a.com",
                             null,
                             null);

    testUser = userRepository.save(testUser);

    final UUID userIdBeforeUpdate = testUser.getId();

    testUser = new User(testUser.getId(),
                        "uptdFName",
                        "uptdLName",
                        "uptdMail@a.com",
                        testUser.getCreatedAt(),
                        null);

    userRepository.save(testUser);
    testUser = userRepository.findById(testUser.getId()).get();

    assertEquals(userIdBeforeUpdate, testUser.getId());
    assertEquals("uptdFName", testUser.getFirstName());
    assertEquals("uptdLName", testUser.getLastName());
    assertEquals("uptdMail@a.com", testUser.getEmail());
  }
}