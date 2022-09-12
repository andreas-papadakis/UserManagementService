package com.agileactors.usermanagementservice.exception;

/**
 * Custom exception for not finding user in DB.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}