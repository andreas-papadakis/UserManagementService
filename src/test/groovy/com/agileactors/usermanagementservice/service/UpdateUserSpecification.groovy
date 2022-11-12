package com.agileactors.usermanagementservice.service

import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.validations.Validator
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

@SpringBootTest
class UpdateUserSpecification extends Specification {
  private UserRepository userRepository       = Mock()
  private Validator validator                 = Mock()
  private ConversionService conversionService = Mock()
  @Subject
  private UserServiceImpl userService

  /**
   * Setup user service before any specification
   */
  def setup() {
    userService = new UserServiceImpl(userRepository, validator, conversionService)
  }

  def "should update user"() {
    given: "a new update user request arrived"
    UpdateUserRequestDto userRequestDto = new UpdateUserRequestDto(UUID.randomUUID(),
                                                                   "updatedFirstName",
                                                                   "updatedLastName",
                                                                   "a@a.com")

    and: "the user to be updated exists in DB"
    User existingUser = new User(userRequestDto.userId,
                                 "originalFirstName",
                                 "originalLastName",
                                 "originalEmail",
                                 LocalDateTime.now(),
                                 null)

    and: "a demo user has been setup representing the user's state after the update"
    def demoUser = new User(userRequestDto.userId,
                            userRequestDto.firstName,
                            userRequestDto.lastName,
                            userRequestDto.email,
                            existingUser.createdAt,
                            null)

    when: "service layer updates the user"
    userService.updateUser(userRequestDto)

    then: "to achieve that, validateEmail was called once"
    1 * validator.validateEmail(userRequestDto.email)

    then: "findById was called once to retrieve the user"
    1 * userRepository.findById(userRequestDto.userId) >> Optional.of(existingUser)

    then: "save called once to update the user"
    1 * userRepository.save(demoUser)

    then: "no other method is called"
    0 * _
  }

  /**
   * After any specification delete the user service
   */
  def cleanup() {
    userService = null
  }
}