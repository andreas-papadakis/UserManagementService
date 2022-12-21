package com.agileactors.usermanagementservice.delete

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class DeleteSpec extends Specification {
  @Autowired
  private MockMvc mockMvc
  @Autowired
  private UserRepository userRepository
  private def user1
  private def user2

  /**
   * Before the run of any specification create and save 2 users
   */
  def setup() {
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

    userRepository.save(user1)
    userRepository.save(user2)
  }

  def "should delete all users"() {
    given: "I try to delete all users"
    def result = mockMvc.perform(delete("/api/users"))
                                 .andReturn()

    expect: "request was successfully completed"
    result.response.status == HttpStatus.OK.value
    and: "response content is as expected"
    result.response.contentAsString == ""
    and: "the DB has no users"
    userRepository.count() == 0
  }

  def "should delete user by ID"() {
    given: "I try to delete all users"
    def result = mockMvc.perform(delete("/api/users/{id}", user1.id))
                                 .andReturn()

    expect: "request was successfully completed"
    result.response.status == HttpStatus.OK.value
    and: "response content is as expected"
    result.response.contentAsString == ""
    and: "the user no longer exists"
    userRepository.findById(user1.id).empty
  }

  def "should fail to delete user because does not exist"() {
    given: "user1 is removed from DB"
    userRepository.deleteById(user1.id)
    and: "I try to delete that user again"
    def result = mockMvc.perform(delete("/api/users/{id}", user1.id))
                                 .andReturn()

    expect: "request failed to complete successfully"
    result.response.status == HttpStatus.NOT_FOUND.value
    and:"response content is as expected"
    result.response.contentAsString.contains("No " + user1.class + " entity with id " + user1.id +
                                             " exists!")
    result.response.contentAsString.contains("\"httpStatus\":\"NOT_FOUND\"")
  }

  /**
   * After the run of any specification clear the DB and the local variables
   */
  def cleanup() {
    userRepository.deleteAll()

    user1 = null
    user2 = null
  }
}