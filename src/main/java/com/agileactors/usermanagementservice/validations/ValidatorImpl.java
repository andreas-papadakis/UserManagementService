package com.agileactors.usermanagementservice.validations;

import com.agileactors.usermanagementservice.exception.InvalidArgumentException;
import org.springframework.stereotype.Component;

/**
 * Class implementing the {@link com.agileactors.usermanagementservice.validations.Validator}
 * interface.
 */
@Component
class ValidatorImpl implements Validator { //TODO: remove public modifier
  /** {@inheritDoc} */
  public void validateEmail(String email) throws InvalidArgumentException {
    if (email == null || !email.matches("[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+")) {
      throw new InvalidArgumentException("Invalid email.");
    }
  }
}