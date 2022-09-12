package com.agileactors.usermanagementservice.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class responsible for handling exceptions.
 */
@SuppressWarnings("checkstyle:LineLengthCheck")
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
  /**
   * Handle UserNotFoundException.
   *
   * @param userNotFoundException The exception
   *
   * @return ResponseEntity of type ApiException with exception's message, 404 HTTP status and the time it occurred
   */
  @ExceptionHandler (value = UserNotFoundException.class)
  public ResponseEntity<ApiException> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
    return new ResponseEntity<>(new ApiException(userNotFoundException.getMessage(),
                                                 HttpStatus.NOT_FOUND,
                                                 LocalDateTime.now()),
                                HttpStatus.NOT_FOUND);
  }

  /**
   * Handle InvalidArgumentException.
   *
   * @param invalidArgumentException The exception
   *
   * @return ResponseEntity of type ApiException with exception's message, 400 HTTP status and the time it occurred
   */
  @ExceptionHandler (value = InvalidArgumentException.class)
  public ResponseEntity<Object> handleInvalidArgumentException(InvalidArgumentException invalidArgumentException) {
    return new ResponseEntity<>(new ApiException(invalidArgumentException.getMessage(),
                                                 HttpStatus.BAD_REQUEST,
                                                 LocalDateTime.now()),
                                HttpStatus.BAD_REQUEST);
  }
}