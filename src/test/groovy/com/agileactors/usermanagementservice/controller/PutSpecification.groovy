package com.agileactors.usermanagementservice.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto
import com.agileactors.usermanagementservice.dto.UpdateUserResponseDto
import com.agileactors.usermanagementservice.exception.ApiExceptionHandler
import com.agileactors.usermanagementservice.exception.InvalidArgumentException
import com.agileactors.usermanagementservice.exception.UserNotFoundException
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
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
class PutSpecification extends Specification {
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

  def "should update the user"() {
    given: "a valid update request arrived"
    UUID id                                   = UUID.randomUUID()
    UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto(null,
                                                                         "firstName",
                                                                         "lastName",
                                                                         "a@a.com")

    and: "a new request has been setup including the provided id"
    UpdateUserRequestDto updateDto = new UpdateUserRequestDto(id,
                                                              updateUserRequestDto.firstName,
                                                              updateUserRequestDto.lastName,
                                                              updateUserRequestDto.email)

    and: "a demo user has been setup as the response of updateUser()"
    User user = new User(id,
                         updateDto.firstName,
                         updateDto.lastName,
                         updateDto.email,
                         null,
                         null)

    and: "a dto has been setup as the response of convert()"
    UpdateUserResponseDto responseDto = new UpdateUserResponseDto(user.id,
                                                                  user.firstName,
                                                                  user.lastName,
                                                                  user.email,
                                                                  null,
                                                                  null)

    when: "controller executes the put request"
    MvcResult result  = mockMvc.perform(put("/api/users/{id}", id)
                               .contentType("application/json")
                               .content(objectMapper
                                          .writeValueAsString(updateUserRequestDto)))
                               .andReturn()
    def response= result.getResponse()
                              .getContentAsString()

    then: "updateUser() from service is called once"
    1 * userService.updateUser(updateDto) >> user

    then: "convert() is called once to convert the response of updateUser to UpdateUserResponseDto"
    1 * conversionService.convert(user, UpdateUserResponseDto.class) >> responseDto

    then: "no other method is called"
    0 * _

    and: "the request was successfully completed"
    result.getResponse().status == HttpStatus.OK.value()

    and: "the http response message is the expected"
    response == "{\"id\":\"" + responseDto.id +
                "\",\"firstName\":\"" + responseDto.firstName +
                "\",\"lastName\":\"" + responseDto.lastName +
                "\",\"email\":\"" + responseDto.email +
                "\",\"createdAt\":" + responseDto.createdAt +
                ",\"updatedAt\":" + responseDto.updatedAt +
                "}"
  }

  def "should fail to update user because user does not exist"() {
    given: "a valid update request arrived"
    UUID id                                   = UUID.randomUUID()
    UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto(null,
                                                                         "firstName",
                                                                         "lastName",
                                                                         "a@a.com")

    and: "a new request has been setup including the provided id"
    UpdateUserRequestDto updateDto = new UpdateUserRequestDto(id,
                                                              updateUserRequestDto.firstName,
                                                              updateUserRequestDto.lastName,
                                                              updateUserRequestDto.email)

    when: "controller executes the put request"
    MvcResult result  = mockMvc.perform(put("/api/users/{id}", id)
                               .contentType("application/json")
                               .content(objectMapper
                                          .writeValueAsString(updateUserRequestDto)))
                               .andReturn()

    then: "updateUser() from service is called once but since the id does not exist it throws a UserNotFoundException"
    1 * userService.updateUser(updateDto) >> { throw new UserNotFoundException("User with ID: "
                                                                              + updateDto.userId()
                                                                              + " does not exist") }

    then: "no other method is called"
    0 * _

    and: "the exception is included in the http response"
    result.getResolvedException() instanceof UserNotFoundException

    and: "the http status is 404"
    result.getResponse().status == HttpStatus.NOT_FOUND.value()

    and: "the exception's message is contained in the http response"
    result.getResponse()
          .getContentAsString()
          .contains("User with ID: " + updateDto.userId() + " does not exist")
  }

  def "should fail to update user because provided data are invalid"() {
    given: "an invalid update request arrived"
    UUID id                                   = UUID.randomUUID()
    UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto(null,
                                                                         "",
                                                                         "lastName",
                                                                         "a@a.com")

    when: "controller executes the put request"
    MvcResult result  = mockMvc.perform(put("/api/users/{id}", id)
                               .contentType("application/json")
                               .content(objectMapper
                                          .writeValueAsString(updateUserRequestDto)))
                               .andReturn()

    then: "no method is called"
    0 * _

    and: "an InvalidArgumentException is thrown and exception handler catches it"
    result.getResolvedException() instanceof InvalidArgumentException

    and: "the http response is 404"
    result.getResponse().status == HttpStatus.BAD_REQUEST.value()

    and: "the validation error message is contained in http response"
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