package com.agileactors.usermanagementservice.validations;

import com.agileactors.usermanagementservice.exception.InvalidArgumentException;
import org.springframework.stereotype.Component;

@SuppressWarnings("checkstyle:MissingJavadocType")
@Component
public class ValidatorImpl implements Validator {
  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public void validateEmail(String email) {
    if (!email.matches("[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+")) {
      throw new InvalidArgumentException("Invalid email.");
    }
  }
}