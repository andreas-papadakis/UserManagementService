package com.agileactors.usermanagementservice.controller

import com.agileactors.usermanagementservice.exception.UserNotFoundException
import org.springframework.util.MultiValueMap

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.agileactors.usermanagementservice.dto.GetUserResponseDto
import com.agileactors.usermanagementservice.exception.ApiExceptionHandler
import com.agileactors.usermanagementservice.model.GetUserModel
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.service.UserService
import java.time.LocalDateTime
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject

@WebMvcTest(UserManagementServiceApplication.class)
class GetSpecification extends Specification {
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

  def "should get all users"() {
    given: "a get request on /api/users arrived"
    GetUserModel getUserModel = new GetUserModel("lala", "lala")

    and: "a demo user has been setup"
    def demoUser = new User(UUID.randomUUID(),
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(10),
                            RandomStringUtils.randomAlphabetic(10),
                            LocalDateTime.now(),
                            null)

    and: "a demo list has been setup as the return value of getAllUsers()"
    def demoList = new ArrayList<User>(List.of(demoUser, demoUser, demoUser))

    and: "a dto has been setup as the return value of controller"
    def responseDto = new GetUserResponseDto(demoUser.firstName, demoUser.lastName, demoUser.email)

    when: "controller executes the get request"
    MvcResult result = mockMvc.perform(get("/api/users")
                              .param("firstName", getUserModel.firstName)
                              .param("lastName", getUserModel.lastName))
                              .andReturn()
    def response = result.getResponse().getContentAsString()

    then:"getAllUsers was executed with provided getUserModel as parameter"
    1 * userService.getAllUsers(getUserModel) >> demoList

    then: "convert was called as many times as demoList's size to convert each user to dto"
    demoList.size() * conversionService.convert(demoUser, GetUserResponseDto.class) >> responseDto

    then: "no other method is called"
    0 * _

    then: "the get request is successfully completed"
    result.getResponse().status == HttpStatus.OK.value()

    then: "response contains at least as many users as were retrieved by getAllUsers()"
    StringUtils.countMatches(response, demoUser.firstName) == demoList.size()
    StringUtils.countMatches(response, demoUser.lastName)  == demoList.size()
    StringUtils.countMatches(response, demoUser.email)     == demoList.size()

    and: "no other user"
    response.split("},").size() == demoList.size()
  }

  def "should get user by ID"() {
    given: "a get request arrived on /api/users/{id}"
    UUID id = UUID.randomUUID()

    and: "a demo user has been setup as the expected response from service"
    def firstName = RandomStringUtils.randomAlphabetic(10)
    def lastName  = RandomStringUtils.randomAlphabetic(10)
    def email     = RandomStringUtils.randomAlphabetic(10)
    def demoUser = new User(id,
                            firstName,
                            lastName,
                            email,
                            null,
                            null)

    and: "a dto has been setup as the result of convert"
    def dto = new GetUserResponseDto(demoUser.firstName, demoUser.lastName, demoUser.email)

    when: "controller executes the get request"
    MvcResult result = mockMvc.perform(get("/api/users/{id}", id))
                              .andReturn()
    def response = result.getResponse().getContentAsString()

    then: "getUserById was called once with the provided id as parameter"
    1 * userService.getUserById(id) >> demoUser

    then: "conversion service converts once the returned user from getUserById to GetUserResponseDto"
    1 * conversionService.convert(demoUser, GetUserResponseDto.class) >> dto

    then: "no other method is called"
    0 * _

    and: "the get request is successfully completed"
    result.getResponse().status == HttpStatus.OK.value()

    and: "the dto returned from convert method is also returned by getUserById from controller"
    response == "{\"firstName\":\"" + demoUser.firstName +
                "\",\"lastName\":\"" + demoUser.lastName +
                "\",\"email\":\"" + demoUser.email +
                "\"}"
  }

  def "should fail to get user by ID because it does not exist"() {
    given: "an ID that does not exist in DB"
    UUID id = UUID.randomUUID()

    and: "a message for the exception has been setup"
    def msg = "User with ID: " + id + " does not exist"

    when: "controller executes the get request"
    MvcResult result = mockMvc.perform(get("/api/users/{id}", id))
                              .andReturn()

    then: "getUserById from service is called once with provided id as parameter and throws a UserNotFoundException"
    1 * userService.getUserById(id) >> { throw new UserNotFoundException(msg)}

    then: "no other method is called"
    0 * _

    and: "response http status is 404"
    result.getResponse().status == HttpStatus.NOT_FOUND.value()

    and: "the exception's message is contained in the response"
    result.getResponse()
          .getContentAsString()
          .contains(msg)
  }

  /**
   * After any specification delete the controller and mockMvc
   */
  def cleanup() {
    userController = null
    mockMvc        = null
  }
}