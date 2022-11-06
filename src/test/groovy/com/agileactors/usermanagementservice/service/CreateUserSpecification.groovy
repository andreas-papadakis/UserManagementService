package com.agileactors.usermanagementservice.service

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.validations.Validator
import java.time.LocalDateTime
import org.springframework.core.convert.ConversionService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class CreateUserSpecification extends Specification {
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

  def "should save user"() {
    given: "a new create user request arrived"
    CreateUserRequestDto userRequestDto = new CreateUserRequestDto("testFName",
                                                                   "testLName",
                                                                   "a@a.com")

    and: "a user has been setup as the expected response of repository's save method"
    User responseUser = new User(UUID.randomUUID(),
                                 userRequestDto.firstName,
                                 userRequestDto.lastName,
                                 userRequestDto.email,
                                 LocalDateTime.now(),
                                 null)

    when: "the user is saved in DB"
    User savedUser = userService.createUser(userRequestDto)

    then: "validateEmail was called once on create user request's email "
    1 * validator.validateEmail(userRequestDto.email)

    and: "convert was called once on userRequestDto and returns responseUser"
    1 * conversionService.convert(userRequestDto, User.class) >> responseUser

    and: "save was called once on any User and returns the demo user"
    1 * userRepository.save(_ as User) >> responseUser

    and: "the user that was saved in DB is the one requested to be saved"
    savedUser.firstName == responseUser.firstName
    savedUser.lastName  == responseUser.lastName
    savedUser.email     == responseUser.email
  }

  /**
   * After any specification delete the user service
   */
  def cleanup() {
    userService = null
  }
}