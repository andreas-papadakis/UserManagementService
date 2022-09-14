package com.agileactors.usermanagementservice.validations;

import com.agileactors.usermanagementservice.exception.InvalidArgumentException;
import org.springframework.stereotype.Component;

/**
 * Class with methods for validations.
 */
@Component
public class Validator {
  /**
   * Validate email is in correct format. If it's not, throw InvalidArgumentException.
   *
   * @param email The email to validate
   *
   * @throws InvalidArgumentException When email is in incorrect format
   */
  public void validateEmail(String email) {
    if (!email.matches("[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+")) {
      throw new InvalidArgumentException("Invalid email.");
    }
  }
}