package com.agileactors.usermanagementservice.controller

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

    then: "the get request is successfully completed"
    result.getResponse().status == HttpStatus.OK.value()

    then: "response contains at least as many users as were retrieved by getAllUsers()"
    StringUtils.countMatches(response, demoUser.firstName) == demoList.size()
    StringUtils.countMatches(response, demoUser.lastName)  == demoList.size()
    StringUtils.countMatches(response, demoUser.email)     == demoList.size()

    and: "no other user"
    response.split("},").size() == demoList.size()
  }

  /**
   * After any specification delete the controller and mockMvc
   */
  def cleanup() {
    userController = null
    mockMvc        = null
  }
}