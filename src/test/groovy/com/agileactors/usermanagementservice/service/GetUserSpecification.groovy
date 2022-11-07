package com.agileactors.usermanagementservice.service

import com.agileactors.usermanagementservice.exception.UserNotFoundException
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

  def "should get all users by last name"() {
    given: "a request to retrieve those users who match the provided last name from DB arrived"
    GetUserModel getUserModel = new GetUserModel(null, _ as String)

    when: "service method to retrieve the users is called"
    userService.getAllUsers(getUserModel)

    then: "service layer calls only findByLastNameLike method from repository"
    0 * userRepository.findAll()
    0 * userRepository.findByFirstNameLike(_ as String)
    1 * userRepository.findByLastNameLike(_ as String) >> new ArrayList<User>()
    0 * userRepository.findByFirstNameLikeAndLastNameLike(_ as String, _ as String)
  }

  def "should get all users by first and last name"() {
    given: "a request to retrieve those users who match the provided first and last name from DB arrived"
    GetUserModel getUserModel = new GetUserModel(_ as String, _ as String)

    when: "service method to retrieve the users is called"
    userService.getAllUsers(getUserModel)

    then: "service layer calls only findByFirstNameLikeAndLastNameLike method from repository"
    0 * userRepository.findAll()
    0 * userRepository.findByFirstNameLike(_ as String)
    0 * userRepository.findByLastNameLike(_ as String)
    1 * userRepository.findByFirstNameLikeAndLastNameLike(_ as String, _ as String) >>
            new ArrayList<User>()
  }

  def "should successfully get user by Id"() {
    given: "a UUID was provided"
    UUID uuid = UUID.randomUUID()

    and: "a user with such UUID exists in DB"
    def user = new User(uuid, "", "", "", null, null)
    Optional<User> userFound = Optional.of(user)

    when: "service layer tries to retrieve that user"
    userService.getUserById(uuid)

    then: "findById by repository is called and an optional with that user is returned"
    1 * userRepository.findById(uuid) >>> userFound
  }

  def "should throw UserNotFoundException when provided ID does not exist in DB"() {
    given: "the UUID does not exists then optional to be returned will be empty"
    Optional<User> userNotFound = Optional.empty()

    when: "service layer tries to retrieve a user with such ID"
    userService.getUserById(UUID.randomUUID())

    then: "findById by repository is called and return an empty optional"
    1 * userRepository.findById(_ as UUID) >>> userNotFound

    and: "that results in a UserNotFoundException to be thrown"
    thrown(UserNotFoundException)
  }

  /**
   * After any specification delete the user service
   */
  def cleanup() {
    userService = null
  }
}