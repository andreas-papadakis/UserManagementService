package com.agileactors.usermanagementservice.exception;

/**
 * Custom exception for invalid arguments.
 */
public class InvalidArgumentException extends RuntimeException {
  public InvalidArgumentException(String message) {
    super(message);
  }
}