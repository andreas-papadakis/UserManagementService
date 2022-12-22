package com.agileactors.usermanagementservice.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.agileactors.usermanagementservice.dto.SaveUserRequestDto;
import com.agileactors.usermanagementservice.exception.InvalidArgumentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests {@link com.agileactors.usermanagementservice.validations.Validator Validator} interface
 * works as expected.
 */
@SpringBootTest
public class EmailValidationTest {
  /**
   * Tests that e-mail form validation works.
   */
  @Test
  public void testValidationOfeMail() {
    Validator validator = new ValidatorImpl();

    SaveUserRequestDto testUser = new SaveUserRequestDto("lala",
                                                             "lala",
                                                             "aaaa@");

    Exception exception = assertThrows(InvalidArgumentException.class,
            () ->  validator.validateEmail(testUser.email()));

    String expectedMessage = "Invalid email.";

    assertEquals(expectedMessage, exception.getMessage());
  }
}
