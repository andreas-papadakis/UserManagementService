package com.agileactors.usermanagementservice.service

import com.agileactors.usermanagementservice.exception.UserNotFoundException
import com.agileactors.usermanagementservice.model.GetUserModel
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.validations.Validator
import org.apache.commons.lang3.RandomStringUtils
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

    then: "service layer calls findAll method from repository"
    1 * userRepository.findAll() >> new ArrayList<User>()

    then: "no other method is called"
    0 * _
  }

  def "should get all users by first name"() {
    given: "a request to retrieve those users who match the provided first name from DB arrived"
    def firstName       = RandomStringUtils.randomAlphabetic(10)
    GetUserModel getUserModel = new GetUserModel(firstName, null)

    when: "service method to retrieve the users is called"
    userService.getAllUsers(getUserModel)

    then: "service layer calls findByFirstNameLike method from repository with provided first name as parameter"
    1 * userRepository.findByFirstNameLike("%" + firstName + "%") >> new ArrayList<User>()

    then: "no other method is called"
    0 * _
  }

  def "should get all users by last name"() {
    given: "a request to retrieve those users who match the provided last name from DB arrived"
    def lastName        = RandomStringUtils.randomAlphabetic(10)
    GetUserModel getUserModel = new GetUserModel(null, lastName)

    when: "service method to retrieve the users is called"
    userService.getAllUsers(getUserModel)

    then: "service layer calls findByLastNameLike method from repository with provided last name as parameter"
    1 * userRepository.findByLastNameLike("%" + lastName + "%") >> new ArrayList<User>()

    then: "no other method is called"
    0 * _
  }

  def "should get all users by first and last name"() {
    given: "a request to retrieve those users who match the provided first and last name from DB arrived"
    def firstName = RandomStringUtils.randomAlphabetic(10)
    def lastName = RandomStringUtils.randomAlphabetic(10)
    GetUserModel getUserModel = new GetUserModel(firstName, lastName)

    when: "service method to retrieve the users is called"
    userService.getAllUsers(getUserModel)

    then: "service layer calls findByFirstNameLikeAndLastNameLike method from repository with provided first and last names as parameters"
    1 * userRepository.findByFirstNameLikeAndLastNameLike("%" + firstName + "%",
                                                          "%" + lastName + "%") >>
            new ArrayList<User>()

    then: "no other method is called"
    0 * _
  }

  def "should successfully get user by Id"() {
    given: "a UUID was provided"
    UUID uuid = UUID.randomUUID()

    and: "a user with such UUID exists in DB"
    def user = new User(uuid, "", "", "", null, null)

    when: "service layer tries to retrieve that user"
    userService.getUserById(uuid)

    then: "findById by repository is called and an optional with that user is returned"
    1 * userRepository.findById(uuid) >> Optional.of(user)

    then: "no other method is called"
    0 * _
  }

  def "should throw UserNotFoundException when provided ID does not exist in DB"() {
    given: "an ID was provided"
    UUID id = UUID.randomUUID()

    when: "service layer tries to retrieve a user that does not exist"
    userService.getUserById(id)

    then: "findById by repository is called and returns an empty optional"
    1 * userRepository.findById(id) >> Optional.empty()

    then: "that results in a UserNotFoundException to be thrown"
    thrown(UserNotFoundException)

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