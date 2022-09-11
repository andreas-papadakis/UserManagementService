package com.agileactors.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (value = UserNotFoundException.class)
    public ResponseEntity<ApiException> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(new ApiException(userNotFoundException.getMessage(),
                                                     HttpStatus.NOT_FOUND,
                                                     LocalDateTime.now()),
                                    HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (value = InvalidArgumentException.class)
    public ResponseEntity<Object> handleInvalidArgumentException(InvalidArgumentException invalidArgumentException) {
        return new ResponseEntity<>(new ApiException(invalidArgumentException.getMessage(),
                                                     HttpStatus.BAD_REQUEST,
                                                     LocalDateTime.now()),
                                    HttpStatus.BAD_REQUEST);
    }
}