package com.agileactors.usermanagementservice.get

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import com.agileactors.usermanagementservice.dto.GetUserResponseDto
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@EnableSharedInjection
class GetSpec extends Specification {
  @Autowired
  private MockMvc mockMvc
  @Autowired
  @Shared
  private ObjectMapper objectMapper
  @Autowired
  @Shared
  private ConversionService conversionService
  @Autowired
  @Shared
  private UserRepository userRepository
  @Shared
  private def user1
  @Shared
  private def user2
  @Shared
  private def user3
  @Shared
  private def user4
  @Shared
  private def getUser1ResponseDtoJSON
  @Shared
  private def getUser2ResponseDtoJSON
  @Shared
  private def getUser3ResponseDtoJSON
  @Shared
  private def getUser4ResponseDtoJSON

  /**
   * Before run of first specification:
   *  1. Clear the DB
   *  2. Save the 4 users
   *  3. Create the GetUserResponseDto for each user as JSON
   */
  def setupSpec() {
    userRepository.deleteAll()

    user1 = new User(UUID.randomUUID(),
                     "firstName",
                     "lastName",
                     "a@a.com",
                     null,
                     null)
    user2 = new User(UUID.randomUUID(),
                     "firstName",
                     "lala",
                     "a@a.com",
                     null,
                     null)
    user3 = new User(UUID.randomUUID(),
                     "lala",
                     "lastName",
                     "a@a.com",
                     null,
                     null)
    user4 = new User(UUID.randomUUID(),
                     "firstName",
                     "lastName",
                     "b@b.com",
                     null,
                     null)

    userRepository.save(user1)
    userRepository.save(user2)
    userRepository.save(user3)
    userRepository.save(user4)

    getUser1ResponseDtoJSON = objectMapper.writeValueAsString(conversionService
                                                          .convert(user1, GetUserResponseDto.class))
    getUser2ResponseDtoJSON = objectMapper.writeValueAsString(conversionService
                                                          .convert(user2, GetUserResponseDto.class))
    getUser3ResponseDtoJSON = objectMapper.writeValueAsString(conversionService
                                                          .convert(user3, GetUserResponseDto.class))
    getUser4ResponseDtoJSON = objectMapper.writeValueAsString(conversionService
                                                          .convert(user4, GetUserResponseDto.class))
  }

  def "should get users with first name: #firstName and last name: #lastName"() {
    given: "I try to get all users with provided firstName and lastName"
    def result = mockMvc.perform(get("/api/users")
                                 .param("firstName", firstName)
                                 .param("lastName", lastName))
                                 .andReturn()

    expect: "the request was successfully completed"
    result.response.status == HttpStatus.OK.value
    and: "the correct numbers of users was retrieved"
    objectMapper.readValue(result.response.contentAsString, List<GetUserResponseDto>.class).size() == expectedSize
    and: "the correct users were retrieved"
    result.response.contentAsString.contains(a)
    result.response.contentAsString.contains(b)
    result.response.contentAsString.contains(c)
    result.response.contentAsString.contains(d)

    cleanup:
    result = null

    where:
    firstName   | lastName   || expectedSize | a  | b | c | d
    ""          | ""         || 4            | getUser1ResponseDtoJSON | getUser2ResponseDtoJSON | getUser3ResponseDtoJSON | getUser4ResponseDtoJSON
    "firstName" | ""         || 3            | getUser1ResponseDtoJSON | getUser2ResponseDtoJSON | "" | getUser4ResponseDtoJSON
    ""          | "lastName" || 3            | getUser1ResponseDtoJSON | "" | getUser3ResponseDtoJSON | getUser4ResponseDtoJSON
    "firstName" | "lastName" || 2            | getUser1ResponseDtoJSON | "" | "" | getUser4ResponseDtoJSON
  }

  def "should get user by ID"() {
    given: "I try to get a user by ID"
    def result = mockMvc.perform(get("/api/users/{id}", user1.id))
                                 .andReturn()

    expect: "the request was successfully completed"
    result.response.status == HttpStatus.OK.value
    and: "the response is as expected"
    result.response.contentAsString == getUser1ResponseDtoJSON
  }

  def "when an ID does not exist in DB then appropriate message should be returned"() {
    given: "I try to get a user with ID that does not exist"
    def randomUUID = UUID.randomUUID()
    def result = mockMvc.perform(get("/api/users/{id}", randomUUID))
                                 .andReturn()

    expect: "the request failed to complete with 404 HttpStatus"
    result.response.status == HttpStatus.NOT_FOUND.value
    and: "response content is as expected"
    result.response.contentAsString.contains("User with ID: " + randomUUID + " does not exist")
    result.response.contentAsString.contains("\"httpStatus\":\"NOT_FOUND\"")
  }

  /**
   * After the last specification:
   *  1. Clear the DB
   *  2. Clear the variables created in setupSpec
   */
  def cleanupSpec() {
    userRepository.deleteAll()

    user1 = null
    user2 = null
    user3 = null
    user4 = null

    getUser1ResponseDtoJSON = null
    getUser2ResponseDtoJSON = null
    getUser3ResponseDtoJSON = null
    getUser4ResponseDtoJSON = null
  }
}