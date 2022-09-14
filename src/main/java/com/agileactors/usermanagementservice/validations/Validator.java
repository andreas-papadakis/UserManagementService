package com.agileactors.usermanagementservice.validations;

import com.agileactors.usermanagementservice.exception.InvalidArgumentException;

/**
 * Interface to hold all the validation methods.
 */
public interface Validator {
  /**
   * Validate email is in correct format. If it's not, throw InvalidArgumentException.
   *
   * @param email The email to validate
   *
   * @throws InvalidArgumentException When email is in incorrect format
   */
  void validateEmail(String email);
}