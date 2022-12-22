package com.agileactors.usermanagementservice.post

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

import com.agileactors.usermanagementservice.dto.SaveUserRequestDto
import com.agileactors.usermanagementservice.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class PostSpec extends Specification {
  @Autowired
  private MockMvc mockMvc
  @Autowired
  private UserRepository userRepository
  @Autowired
  private ObjectMapper objectMapper

  /**
   * Clear the DB before the run of any specification
   */
  def setup() {
    userRepository.deleteAll()
  }

  def "should save a User"() {
    given: "a valid SaveUserRequestDto"
    def saveUserRequestDto = new SaveUserRequestDto("firstName",
                                                    "lastName",
                                                    "a@a.com")

    when: "I try to save the CreateUserRequestDto"
    def result = mockMvc.perform(post("/api/users")
                                  .contentType("application/json")
                                  .content(objectMapper
                                          .writeValueAsString(saveUserRequestDto)))
                                  .andReturn()

    then: "request completed successfully"
    result.response.status == HttpStatus.OK.value
    and: "response is as expected"
    result.response.contentAsString.contains(saveUserRequestDto.firstName)
    result.response.contentAsString.contains(saveUserRequestDto.lastName)
    result.response.contentAsString.contains(saveUserRequestDto.email)
    and: "check the user is indeed saved with expected data"
    def user = userRepository.findAll().get(0)

    user.firstName == saveUserRequestDto.firstName
    user.lastName  == saveUserRequestDto.lastName
    user.email     == saveUserRequestDto.email
    ChronoUnit.SECONDS.between(LocalDateTime.now(), user.createdAt) <= 1
    ChronoUnit.SECONDS.between(LocalDateTime.now(), user.updatedAt) <= 1
  }

  def "should fail to save user because SaveUserRequestDto is invalid"() {
    given: "an invalid SaveUserRequestDto"
    def saveUserRequestDto = new SaveUserRequestDto("",
                                                        "lastName",
                                                        "a@a.com")

    when: "I try to save the CreateUserRequestDto"
    def result = mockMvc.perform(post("/api/users")
                                 .contentType("application/json")
                                 .content(objectMapper
                                         .writeValueAsString(saveUserRequestDto)))
                                 .andReturn()

    then: "request failed to complete"
    result.response.status == HttpStatus.BAD_REQUEST.value
    and: "response is as expected"
    result.response.contentAsString
                   .contains("First name must not be blank and up to 100 characters.")
    result.response.contentAsString.contains("\"httpStatus\":\"BAD_REQUEST\"")
  }

  /**
   * Clear the DB after the run of any specification
   */
  def cleanup() {
    userRepository.deleteAll()
  }
}