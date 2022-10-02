package com.agileactors.usermanagementservice.exception;

import java.time.LocalDateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class responsible for handling {@link Exception}.
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
  /**
   * Handle {@link com.agileactors.usermanagementservice.exception.UserNotFoundException}.
   *
   * @param userNotFoundException The exception
   *
   * @return {@link ResponseEntity} of type
   *     {@link com.agileactors.usermanagementservice.exception.ApiException} with exception's
   *     message, 404 HTTP status and the time it occurred
   */
  @ExceptionHandler (value = UserNotFoundException.class)
  public ResponseEntity<ApiException> handleUserNotFoundException(UserNotFoundException
                                                                    userNotFoundException) {
    return new ResponseEntity<>(new ApiException(userNotFoundException.getMessage(),
                                                 HttpStatus.NOT_FOUND,
                                                 LocalDateTime.now()),
                                HttpStatus.NOT_FOUND);
  }

  /**
   * Handle {@link com.agileactors.usermanagementservice.exception.InvalidArgumentException}.
   *
   * @param invalidArgumentException The exception
   *
   * @return {@link ResponseEntity} of type
   *     {@link com.agileactors.usermanagementservice.exception.ApiException} with exception's
   *     message, 400 HTTP status and the time it occurred
   */
  @ExceptionHandler (value = InvalidArgumentException.class)
  public ResponseEntity<Object> handleInvalidArgumentException(InvalidArgumentException
                                                                 invalidArgumentException) {
    return new ResponseEntity<>(new ApiException(invalidArgumentException.getMessage(),
                                                 HttpStatus.BAD_REQUEST,
                                                 LocalDateTime.now()),
                                HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle {@link IllegalArgumentException}.
   *
   * @param illegalArgumentException The exception
   *
   * @return {@link ResponseEntity} of type
   *         {@link com.agileactors.usermanagementservice.exception.ApiException} with standard
   *         message, 500 HTTP status and the time it occurred
   */
  @ExceptionHandler (value = IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException
                                                                 illegalArgumentException) {
    System.err.println(illegalArgumentException.getMessage()); //TODO: remove
    return new ResponseEntity<>(new ApiException("Internal server error",
                                                 HttpStatus.INTERNAL_SERVER_ERROR,
                                                 LocalDateTime.now()),
                                HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle {@link EmptyResultDataAccessException}.
   *
   * @param emptyResultDataAccessException The exception
   *
   * @return {@link ResponseEntity} of type
   *         {@link com.agileactors.usermanagementservice.exception.ApiException} with the
   *         exception's message, 404 HTTP status and the time it occurred
   */
  @ExceptionHandler (value = EmptyResultDataAccessException.class)
  public ResponseEntity<Object> handleEmptyResultDataAccessException(
          EmptyResultDataAccessException emptyResultDataAccessException) {
    return new ResponseEntity<>(new ApiException(emptyResultDataAccessException.getMessage(),
                                                 HttpStatus.NOT_FOUND,
                                                 LocalDateTime.now()),
                                HttpStatus.NOT_FOUND);
  }
}