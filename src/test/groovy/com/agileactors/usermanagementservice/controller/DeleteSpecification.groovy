package com.agileactors.usermanagementservice.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.agileactors.usermanagementservice.exception.ApiExceptionHandler
import com.agileactors.usermanagementservice.service.UserService
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.convert.ConversionService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject

@WebMvcTest(UserManagementServiceApplication.class)
class DeleteSpecification extends Specification {
  private UserService userService             = Mock()
  private ConversionService conversionService = Mock()
  private MockMvc mockMvc
  @Subject
  private UserController userController

  /**
   * Setup controller and mockMvc before any specification
   */
  def setup() {
    userController = new UserController(userService, conversionService)
    mockMvc        = MockMvcBuilders.standaloneSetup(userController)
                                    .setControllerAdvice(ApiExceptionHandler.class)
                                    .build()
  }

  def "should delete all users"() {
    when: "a delete request arrives"
    MvcResult result = mockMvc.perform(delete("/api/users"))
                              .andReturn()

    then: "deleteAllUsers() from service is called"
    1 * userService.deleteAllUsers()

    then: "no other method is called"
    0 * _

    and: "the response code is 200"
    result.getResponse().status == HttpStatus.OK.value()
  }

  def "should delete user by id"() {
    given: "an id was provided"
    UUID id = UUID.randomUUID()

    when: "a delete user by id request arrives"
    MvcResult result = mockMvc.perform(delete("/api/users/{id}", id))
                              .andReturn()

    then: "deleteUser() from service is called once"
    1 * userService.deleteUser(id)

    then: "no other method is called"
    0 * _

    and: "the request was successfully completed"
    result.getResponse().status == HttpStatus.OK.value()
  }

  def "should fail to delete user by id because it does not exist"() {
    given: "an id was provided"
    UUID id = UUID.randomUUID()

    when: "a delete user by id request arrives"
    MvcResult result = mockMvc.perform(delete("/api/users/{id}", id))
                              .andReturn()

    then: "deleteUser() from service is called once but throws an EmptyResultDataAccessException"
    1 * userService.deleteUser(id) >> { throw new EmptyResultDataAccessException("No class " +
            "com.agileactors.usermanagementservice.model.User entity with id " + id  + " exists!",1)
            }

    then: "no other method is called"
    0 * _

    and: "the request return http status of 404"
    result.getResponse().status == HttpStatus.NOT_FOUND.value()
  }
}