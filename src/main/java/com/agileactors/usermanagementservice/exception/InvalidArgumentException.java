package com.agileactors.usermanagementservice.exception;

/**
 * Custom {@link RuntimeException} for invalid arguments.
 */
public class InvalidArgumentException extends RuntimeException {
  public InvalidArgumentException(String message) {
    super(message);
  }
}