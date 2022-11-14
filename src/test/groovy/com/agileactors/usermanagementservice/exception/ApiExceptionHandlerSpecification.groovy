package com.agileactors.usermanagementservice.exception

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@SpringBootTest
class ApiExceptionHandlerSpecification extends Specification {
  @Autowired
  private ApiExceptionHandler exceptionHandler

  def "should handle UserNotFoundException"() {
    given: "a new UserNotFoundException was created"
    UserNotFoundException e = new UserNotFoundException(_ as String)

    when: "exception handler handles the exception"
    ResponseEntity<ApiException> responseEntity = exceptionHandler.handleUserNotFoundException(e)

    then: "the response was created as expected"
    responseEntity.statusCode      == HttpStatus.NOT_FOUND
    responseEntity.body.message    == e.message
    responseEntity.body.httpStatus == responseEntity.statusCode
  }

  def "should handle InvalidArgumentException"() {
    given: "a new IllegalArgumentException was created"
    IllegalArgumentException e = new IllegalArgumentException(_ as String)

    when: "exception handler handles the exception"
    ResponseEntity<ApiException> responseEntity = exceptionHandler.handleIllegalArgumentException(e)

    then: "the response was created as expected"
    responseEntity.statusCode      == HttpStatus.INTERNAL_SERVER_ERROR
    responseEntity.body.message    == "Internal server error"
    responseEntity.body.httpStatus == responseEntity.statusCode
  }

  def "should handle EmptyResultDataAccessException"() {
    given: "a new EmptyResultDataAccessException was created"
    EmptyResultDataAccessException e = new EmptyResultDataAccessException(_ as String, 1)

    when: "exception handler handles the exception"
    ResponseEntity<ApiException> responseEntity = exceptionHandler
                                                    .handleEmptyResultDataAccessException(e)

    then: "the response was created as expected"
    responseEntity.statusCode      == HttpStatus.NOT_FOUND
    responseEntity.body.message    == e.message
    responseEntity.body.httpStatus == responseEntity.statusCode
  }
}