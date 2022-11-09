package com.agileactors.usermanagementservice.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import com.agileactors.usermanagementservice.dto.CreateUserResponseDto
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.service.UserService
import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.convert.ConversionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject

@WebMvcTest(UserManagementServiceApplication.class)
class PostSpecification extends Specification {
  private UserService userService             = Mock()
  private ConversionService conversionService = Mock()
  private MockMvc mockMvc
  @Autowired
  private ObjectMapper objectMapper
  @Subject
  private UserController userController

  /**
   * Setup controller and mockMvc before any specification
   */
  def setup() {
    userController = new UserController(userService, conversionService)
    mockMvc        = MockMvcBuilders.standaloneSetup(userController).build()
  }

  def "on valid create user request, controller should create a user and return Http status 200"() {
    given: "the createUserRequestDto is valid"
    CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("firstName",
                                                                         "lastName",
                                                                         "a@a.com")
    when: "controller executes the post request"
    MvcResult result = mockMvc.perform(post("/api/users")
                                           .contentType("application/json")
                                           .content(objectMapper
                                                      .writeValueAsString(createUserRequestDto)))
                                           .andReturn()

    then: "Http status of 200 is returned"
    result.getResponse().status == 200

    and: "createUser from service layer was called once"
    1 * userService.createUser(_ as CreateUserRequestDto) >> new User()

    and: "controller converts the returned user from createUser to CreateUserResponseDto"
    1 * conversionService.convert(_ as User, CreateUserResponseDto.class)
  }

  /**
   * After any specification delete the controller and mockMvc
   */
  def cleanup() {
    userController = null
    mockMvc        = null
  }
}