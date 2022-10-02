package com.agileactors.usermanagementservice.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.exception.InvalidArgumentException;
import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import com.agileactors.usermanagementservice.validations.ValidatorImpl;
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
public class CreateUserRepositoryTest {
  private final UserRepository userRepository;

  @Autowired
  public CreateUserRepositoryTest(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Tests correct creation of {@link com.agileactors.usermanagementservice.model.User user}.
   * Creates user in DB and tries to get by ID.
   */
  @Test
  public void testCreateUser() {
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
    CreateUserRequestDto testUser = new CreateUserRequestDto("",
                                                             "testLName",
                                                             "a@a.com");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<CreateUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for no larger than 100 chars first name works.
   */
  @Test
  public void testValidationNoLargerThan1000CharsFirstName() {
    CreateUserRequestDto testUser = new CreateUserRequestDto(
                                          RandomStringUtils.randomAlphabetic(101),
                                          "testLName",
                                          "a@a.com");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<CreateUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for not blank last name works.
   */
  @Test
  public void testValidationBlankLastName() {
    CreateUserRequestDto testUser = new CreateUserRequestDto("lala",
                                                             " ",
                                                             "a@a.com");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<CreateUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for no larger than 100 chars last name works.
   */
  @Test
  public void testValidationNoLargerThan1000CharsLastName() {
    CreateUserRequestDto testUser = new CreateUserRequestDto(
                                          "lala",
                                          RandomStringUtils.randomAlphabetic(101),
                                          "a@a.com");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<CreateUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for not blank last name works.
   */
  @Test
  public void testValidationBlankeMail() {
    CreateUserRequestDto testUser = new CreateUserRequestDto("lala",
                                                             "lala",
                                                             " ");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<CreateUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that validation for no larger than 100 chars e-mail works.
   */
  @Test
  public void testValidationNoLargerThan1000CharseMail() {
    CreateUserRequestDto testUser = new CreateUserRequestDto(
                                          "lala",
                                          "lala",
                                          RandomStringUtils.randomAlphabetic(101));

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<CreateUserRequestDto>> violations = validator.validate(testUser);

    assertFalse(violations.isEmpty());
  }

  /**
   * Tests that e-mail form validation works.
   */
  @Test
  public void testValidationOfeMail() {
    com.agileactors.usermanagementservice.validations.Validator validator = new ValidatorImpl();

    CreateUserRequestDto testUser = new CreateUserRequestDto("lala",
                                                             "lala",
                                                             "aaaa@");
        
    Exception exception = assertThrows(InvalidArgumentException.class,
                                       () ->  validator.validateEmail(testUser.email()));

    String expectedMessage = "Invalid email.";

    assertEquals(expectedMessage, exception.getMessage());
  }
}