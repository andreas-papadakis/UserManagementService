package com.agileactors.usermanagementservice.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.agileactors.usermanagementservice.dto.SaveUserRequestDto;
import com.agileactors.usermanagementservice.model.User;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests {@link com.agileactors.usermanagementservice.model.User users} are being correctly saved in
 * DB. Also, checks that validations on DTOs work as expected.
 */
@SpringBootTest
public class PostUserTest {
  private final UserRepository userRepository;

  @Autowired
  public PostUserTest(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Tests correct creation of {@link com.agileactors.usermanagementservice.model.User user}.
   * Creates user in DB and tries to get by ID.
   */
  @Test
  public void testSaveUser() {
    User testUser = new User(UUID.randomUUID(),
                             "testFName",
                             "testLName",
                             "a@a.com",
                             null,
                             null);

    userRepository.save(testUser);

    assertTrue(userRepository.findById(testUser.getId()).isPresent());
  }

  /**
   * Tests that validation for not blank first name works.
   */
  @Test
  public void testValidationBlankFirstName() {
    SaveUserRequestDto testUser = new SaveUserRequestDto("",
                                                             "testLName",
                                                             "a@a.com");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<SaveUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for no larger than 100 chars first name works.
   */
  @Test
  public void testValidationNoLargerThan1000CharsFirstName() {
    SaveUserRequestDto testUser = new SaveUserRequestDto(
                                          RandomStringUtils.randomAlphabetic(101),
                                          "testLName",
                                          "a@a.com");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<SaveUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for not blank last name works.
   */
  @Test
  public void testValidationBlankLastName() {
    SaveUserRequestDto testUser = new SaveUserRequestDto("lala",
                                                             " ",
                                                             "a@a.com");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<SaveUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for no larger than 100 chars last name works.
   */
  @Test
  public void testValidationNoLargerThan1000CharsLastName() {
    SaveUserRequestDto testUser = new SaveUserRequestDto(
                                          "lala",
                                          RandomStringUtils.randomAlphabetic(101),
                                          "a@a.com");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<SaveUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for not blank last name works.
   */
  @Test
  public void testValidationBlankeMail() {
    SaveUserRequestDto testUser = new SaveUserRequestDto("lala",
                                                             "lala",
                                                             " ");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<SaveUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for no larger than 100 chars e-mail works.
   */
  @Test
  public void testValidationNoLargerThan1000CharseMail() {
    SaveUserRequestDto testUser = new SaveUserRequestDto(
                                          "lala",
                                          "lala",
                                          RandomStringUtils.randomAlphabetic(101));

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<SaveUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }
}