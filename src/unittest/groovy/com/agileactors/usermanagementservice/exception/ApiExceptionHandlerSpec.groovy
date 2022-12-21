package com.agileactors.usermanagementservice.exception

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import spock.lang.Specification

class ApiExceptionHandlerSpec extends Specification {
  private def apiExceptionHandler = new ApiExceptionHandler()

  def "should handle UserNotFoundException"() {
    given: "a UserNotFoundException"
    def userNotFoundException = new UserNotFoundException(_ as String)

    when: "exception handler handles the exception"
    def responseEntity = apiExceptionHandler.
                                                  handleUserNotFoundException(userNotFoundException)

    then: "exception's message is passed into responseEntity body's message"
    responseEntity.body.message == userNotFoundException.message
    and: "404 is passed into responseEntity body's HttpStatus"
    responseEntity.body.httpStatus == HttpStatus.NOT_FOUND
    and: "responseEntity's HttpStatus is also 404"
    responseEntity.statusCode == HttpStatus.NOT_FOUND
  }

  def "should handle InvalidArgumentException"() {
    given: "an InvalidArgumentException"
    def invalidArgumentException = new InvalidArgumentException(_ as String)

    when: "exception handler handles the exception"
    def responseEntity = apiExceptionHandler.
                                            handleInvalidArgumentException(invalidArgumentException)

    then: "exception's message is passed into responseEntity body's message"
    responseEntity.body.message == invalidArgumentException.message
    and: "400 is passed into responseEntity body's HttpStatus"
    responseEntity.body.httpStatus == HttpStatus.BAD_REQUEST
    and: "responseEntity's HttpStatus is also 400"
    responseEntity.statusCode == HttpStatus.BAD_REQUEST
  }

  def "should handle IllegalArgumentException"() {
    when: "exception handler handles the exception"
    def responseEntity = apiExceptionHandler.handleIllegalArgumentException()

    then: "Internal server error is passed as responseEntity body's message"
    responseEntity.body.message == "Internal server error"
    and: "500 is passed into responseEntity body's HttpStatus"
    responseEntity.body.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR
    and: "responseEntity's HttpStatus is also 500"
    responseEntity.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
  }

  def "should handle EmptyResultDataAccessException"() {
    given: "an EmptyResultDataAccessException"
    def emptyResultDataAccessException = new EmptyResultDataAccessException(_ as String, 1)

    when: "exception handler handles the exception"
    def responseEntity = apiExceptionHandler.
                                handleEmptyResultDataAccessException(emptyResultDataAccessException)

    then: "exception's message is passed into responseEntity body's message"
    responseEntity.body.message == emptyResultDataAccessException.message
    and: "404 is passed into responseEntity body's HttpStatus"
    responseEntity.body.httpStatus == HttpStatus.NOT_FOUND
    and: "responseEntity's HttpStatus is also 404"
    responseEntity.statusCode == HttpStatus.NOT_FOUND
  }
}