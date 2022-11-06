package com.agileactors.usermanagementservice.service

import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.validations.Validator
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class DeleteUserSpecification extends Specification {
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

  def "should delete user"() {
    given: "a UUID was provided"
    UUID uuid = UUID.randomUUID()

    when: "service layer tries to delete the user linked to that UUID"
    userService.deleteUser(uuid)

    then:
    1 * userRepository.deleteById(uuid)
  }

  /**
   * After any specification delete the user service
   */
  def cleanup() {
    userService = null
  }
}