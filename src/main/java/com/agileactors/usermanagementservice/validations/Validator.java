package com.agileactors.usermanagementservice.validations;

import com.agileactors.usermanagementservice.exception.InvalidArgumentException;

/**
 * Interface to hold all the validation methods.
 */
public interface Validator {
  /**
   * Validates email is in correct format. If it's not, throw
   * {@link com.agileactors.usermanagementservice.exception.InvalidArgumentException
   * InvalidArgumentException}.
   *
   * @param email The email to validate
   *
   * @throws InvalidArgumentException When email is in incorrect format
   */
  void validateEmail(String email);
}