package com.agileactors.usermanagementservice.service

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.validations.Validator
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

    when: "the user is saved in DB"
    userService.createUser(userRequestDto)

    then: "validateEmail is called once on create user request's email "
    1 * validator.validateEmail(userRequestDto.email)

    and: "convert is called once on userRequestDto and returns a user"
    1 * conversionService.convert(userRequestDto, User.class) >> new User()

    and: "save is called once on any user"
    1 * userRepository.save(_ as User)
  }

  /**
   * After any specification delete the user service
   */
  def cleanup() {
    userService = null
  }
}