package com.agileactors.usermanagementservice.exception;

/**
 * Custom {@link RuntimeException} for not finding user in DB.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}