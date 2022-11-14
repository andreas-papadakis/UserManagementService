package com.agileactors.usermanagementservice.exception;

/**
 * Custom {@link RuntimeException} for not finding
 * {@link com.agileactors.usermanagementservice.model.User user} in DB.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}