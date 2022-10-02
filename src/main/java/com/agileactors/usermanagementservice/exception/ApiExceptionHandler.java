package com.agileactors.usermanagementservice.exception;

import java.time.LocalDateTime;
import java.util.UUID;
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
   * Handles {@link com.agileactors.usermanagementservice.exception.UserNotFoundException}.<br>
   * The exception is thrown when the ID of a
   * {@link com.agileactors.usermanagementservice.model.User user} was not found in DB.<br>
   * Creates and returns a new {@link ResponseEntity}<
   * {@link com.agileactors.usermanagementservice.exception.ApiException}> with exception's
   * message, 404 HTTP status and the time it occurred
   *
   * @param userNotFoundException The exception thrown when the ID of
   *                              {@link com.agileactors.usermanagementservice.model.User user} was
   *                              not found in DB
   *
   * @return {@link ResponseEntity} of type
   *         {@link com.agileactors.usermanagementservice.exception.ApiException} with exception's
   *         message, 404 HTTP status and the time it occurred
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
   * Handles {@link com.agileactors.usermanagementservice.exception.InvalidArgumentException}.<br>
   * The exception is thrown when the arguments that were given are invalid.<br>
   * Creates and returns a new {@link ResponseEntity}<
   * {@link com.agileactors.usermanagementservice.exception.ApiException}> with exception's
   * message, 400 HTTP status and the time it occurred
   *
   * @param invalidArgumentException The exception thrown when an argument is invalid
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
   * Handles {@link IllegalArgumentException}.<br>
   * The exception is thrown when an illegal or inappropriate parameter has been passed to a method.
   * Creates and returns a new {@link ResponseEntity}<
   * {@link com.agileactors.usermanagementservice.exception.ApiException}> with a standard message,
   * 400 HTTP status and the time it occurred
   *
   * @param illegalArgumentException The exception thrown when an illegal or inappropriate parameter
   *                                 has been passed to a method
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
   * Handles {@link EmptyResultDataAccessException}.<br>
   * The exception is thrown when a method tries to access data on an empty data structure (e.g.
   * {@link com.agileactors.usermanagementservice.service.UserService#deleteUser(UUID)
   * deletion of a specific user}).<br>
   * Creates and returns a new {@link ResponseEntity}<
   * {@link com.agileactors.usermanagementservice.exception.ApiException}> with the exception's
   * message, 404 HTTP status and the time it occurred
   *
   * @param emptyResultDataAccessException The exception thrown when a method tries to access data
   *                                       on an empty data structure
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