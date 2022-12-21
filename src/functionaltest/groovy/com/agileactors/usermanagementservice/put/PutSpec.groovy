package com.agileactors.usermanagementservice.put

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class PutSpec extends Specification {
  @Autowired
  private MockMvc mockMvc
  @Autowired
  private UserRepository userRepository
  @Autowired
  private ObjectMapper objectMapper
  @Shared
  private User user

  /**
   * Clear the DB before the run of any specification and save a user
   */
  def setup() {
    userRepository.deleteAll()

    user = new User(UUID.randomUUID(),
                    "firstName",
                    "lastName",
                    "a@a.com",
                    null,
                    null)

    userRepository.save(user)
  }

  def "should update user"() {
    given: "a valid UpdateUserRequestDto"
    def updateUserRequestDto = new UpdateUserRequestDto(user.id,
                                                        "newFirstName",
                                                        "newLastName",
                                                        "b@b.com")

    when: "I try to update a user"
    def result  = mockMvc.perform(put("/api/users/{id}", user.id)
                                  .contentType("application/json")
                                  .content(objectMapper
                                          .writeValueAsString(updateUserRequestDto)))
                                  .andReturn()

    then: "request was successfully completed"
    result.response.status == HttpStatus.OK.value
    and: "response is as expected"
    result.response.contentAsString.contains(updateUserRequestDto.firstName)
    result.response.contentAsString.contains(updateUserRequestDto.lastName)
    result.response.contentAsString.contains(updateUserRequestDto.email)
    and: "check the user is indeed updated"
    def user = userRepository.findAll().get(0)

    user.firstName == updateUserRequestDto.firstName
    user.lastName  == updateUserRequestDto.lastName
    user.email     == updateUserRequestDto.email
    ChronoUnit.SECONDS.between(LocalDateTime.now(), user.createdAt) <= 1
    ChronoUnit.SECONDS.between(LocalDateTime.now(), user.updatedAt) <= 1
  }

  def "should fail to update user because does not exist"() {
    given: "an empty DB"
    userRepository.deleteAll()
    and: "a valid UpdateUserRequestDto"
    def randomUUID = UUID.randomUUID()
    def updateUserRequestDto = new UpdateUserRequestDto(randomUUID,
                                                        "newFirstName",
                                                        "newLastName",
                                                        "b@b.com")

    when: "I try to update a user"
    def result  = mockMvc.perform(put("/api/users/{id}", randomUUID)
                                  .contentType("application/json")
                                  .content(objectMapper
                                          .writeValueAsString(updateUserRequestDto)))
                                  .andReturn()

    then:
    result.response.status == HttpStatus.NOT_FOUND.value
    and: "response content is as expected"
    result.response.contentAsString.contains("User with ID: " + randomUUID + " does not exist")
    result.response.contentAsString.contains("\"httpStatus\":\"NOT_FOUND\"")
  }

  def "should fail to update user because UpdateRequestDto is invalid"() {
    given: "an invalid UpdateUserRequestDto"
    def updateUserRequestDto = new UpdateUserRequestDto(UUID.randomUUID(),
                                                        "",
                                                        "lastName",
                                                        "b@b.com")

    when: "I try to update a user"
    def result  = mockMvc.perform(put("/api/users/{id}",
                                                updateUserRequestDto.userId)
                                  .contentType("application/json")
                                  .content(objectMapper
                                          .writeValueAsString(updateUserRequestDto)))
                                  .andReturn()

    then: "request failed to complete"
    result.response.status == HttpStatus.BAD_REQUEST.value
    and: "response is as expected"
    result.response.contentAsString
                   .contains("First name must not be blank and up to 100 characters.")
    result.response.contentAsString.contains("\"httpStatus\":\"BAD_REQUEST\"")
  }
}