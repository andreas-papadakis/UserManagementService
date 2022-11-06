package com.agileactors.usermanagementservice.service

import com.agileactors.usermanagementservice.model.GetUserModel
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.validations.Validator
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class GetUserSpecification extends Specification {
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

  def "should get all users"() {
    given: "a request to retrieve all users from DB arrived"
    GetUserModel getUserModel = new GetUserModel(null, null)

    when: "service method to retrieve the users is called"
    userService.getAllUsers(getUserModel)

    then: "service layer calls only findAll method from repository"
    1 * userRepository.findAll() >> new ArrayList<User>()
    0 * userRepository.findByFirstNameLike(_ as String)
    0 * userRepository.findByLastNameLike(_ as String)
    0 * userRepository.findByFirstNameLikeAndLastNameLike(_ as String, _ as String)
  }

  def "should get all users by first name"() {
    given: "a request to retrieve those users who match the provided first name from DB arrived"
    GetUserModel getUserModel = new GetUserModel(_ as String, null)

    when: "service method to retrieve the users is called"
    userService.getAllUsers(getUserModel)

    then: "service layer calls only findByFirstNameLike method from repository"
    0 * userRepository.findAll()
    1 * userRepository.findByFirstNameLike(_ as String) >> new ArrayList<User>()
    0 * userRepository.findByLastNameLike(_ as String)
    0 * userRepository.findByFirstNameLikeAndLastNameLike(_ as String, _ as String)
  }

  def cleanup() {
    userService = null
  }
}