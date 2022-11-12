package com.agileactors.usermanagementservice.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import com.agileactors.usermanagementservice.dto.CreateUserResponseDto
import com.agileactors.usermanagementservice.exception.ApiExceptionHandler
import com.agileactors.usermanagementservice.exception.InvalidArgumentException
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.service.UserService
import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
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
    mockMvc        = MockMvcBuilders.standaloneSetup(userController)
                                    .setControllerAdvice(ApiExceptionHandler.class)
                                    .build()
  }

  def "on valid create user request, controller should create a user and return Http status 200"() {
    given: "the createUserRequestDto is valid"
    CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("firstName",
                                                                         "lastName",
                                                                         "a@a.com")

    and: "a demo user has been setup as the return value of createUser method of service"
    def now = LocalDateTime.now()
    def demoUser        = new User(UUID.randomUUID(),
                                   createUserRequestDto.firstName,
                                   createUserRequestDto.lastName,
                                   createUserRequestDto.email,
                                   now,
                                   now.plusDays(2))

    and: "a dto has been setup as the return value of conversionService in controller"
    def responseDto = new CreateUserResponseDto(demoUser.id,
                                                demoUser.firstName,
                                                demoUser.lastName,
                                                demoUser.email,
                                                demoUser.createdAt,
                                                demoUser.updatedAt)

    when: "controller executes the post request"
    MvcResult result = mockMvc.perform(post("/api/users")
                                           .contentType("application/json")
                                           .content(objectMapper
                                                      .writeValueAsString(createUserRequestDto)))
                                           .andReturn()

    then: "createUser from service layer was called once"
    1 * userService.createUser(createUserRequestDto) >> demoUser

    then: "controller converts the returned user from createUser to CreateUserResponseDto"
    1 * conversionService.convert(demoUser, CreateUserResponseDto.class) >> responseDto

    then: "no other method is called"
    0 * _

    and: "Http status of 200 is returned"
    result.getResponse().status == HttpStatus.OK.value()

    and: "the data of dto are contained in response"
    String createdAt = "[" + responseDto.createdAt().year +
                       "," + responseDto.createdAt().monthValue +
                       "," + responseDto.createdAt().dayOfMonth +
                       "," + responseDto.createdAt().hour +
                       "," + responseDto.createdAt().minute +
                       "," + responseDto.createdAt().second
    String updatedAt = "[" + responseDto.updatedAt().year +
                       "," + responseDto.updatedAt().monthValue +
                       "," + responseDto.updatedAt().dayOfMonth +
                       "," + responseDto.updatedAt().hour +
                       "," + responseDto.updatedAt().minute +
                       "," + responseDto.updatedAt().second
    def responseContent = result.getResponse().getContentAsString()
    responseContent.contains(responseDto.id as String)
    responseContent.contains(responseDto.firstName)
    responseContent.contains(responseDto.lastName)
    responseContent.contains(responseDto.email)
    responseContent.contains(createdAt)
    responseContent.contains(updatedAt)
  }

  def "on invalid create user request, controller should throw an exception and exception handler return http status of 400"() {
    given: "an invalid createUserRequestDto"
    CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("",
                                                                         "lastName",
                                                                         "a@a.com")

    when: "controller executes the post request"
    MvcResult result = mockMvc.perform(post("/api/users")
                   .contentType("application/json")
                   .content(objectMapper
                              .writeValueAsString(createUserRequestDto)))
                   .andReturn()

    then: "an InvalidArgumentException was thrown and exception handler caught it"
    result.getResolvedException() instanceof InvalidArgumentException

    and: "it set an http status of 400"
    result.getResponse().status == HttpStatus.BAD_REQUEST.value()

    and:
    result.getResponse()
          .getContentAsString()
          .contains("First name must not be blank and up to 100 characters.")
  }

  /**
   * After any specification delete the controller and mockMvc
   */
  def cleanup() {
    userController = null
    mockMvc        = null
  }
}