package com.agileactors.usermanagementservice.service

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto
import com.agileactors.usermanagementservice.exception.UserNotFoundException
import com.agileactors.usermanagementservice.model.GetUserModel
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.validations.Validator
import java.time.LocalDateTime
import org.springframework.core.convert.ConversionService
import spock.lang.Specification
import spock.lang.Subject

class UserServiceImplSpec extends Specification {
  private def userRepository    = Mock(UserRepository)
  private def validator         = Mock(Validator)
  private def conversionService = Mock(ConversionService)
  @Subject
  private def userService       = new UserServiceImpl(userRepository, validator, conversionService)

  def "should save user"() {
    given: "a SaveUserRequestDto"
    def saveUserRequestDto = new CreateUserRequestDto(_ as String, _ as String, _ as String)
    and: "a User"
    def user = new User()

    when: "save method is called with provided SaveUserRequestDto"
    userService.createUser(saveUserRequestDto)

    then: "validateEmail is called once to validate CreateUserRequestDto e-mail"
    1 * validator.validateEmail(saveUserRequestDto.email)

    then: "convert is called once to convert the CreateUserRequestDto to User"
    1 * conversionService.convert(saveUserRequestDto, User.class) >> user

    then: "save method from repository is called once with the user convert returned"
    1 * userRepository.save(user)

    then: "no other method is called"
    0 * _

    cleanup:
    saveUserRequestDto = null
    user               = null
  }

  def "should get all users"() {
    given: "a GetUserModel with both parameters null"
    def getUserModel = new GetUserModel(null, null)

    when: "getAllUsers method is called with provided GetUserModel"
    userService.getAllUsers(getUserModel)

    then: "findAll method from repository is called once"
    1 * userRepository.findAll()

    then: "no other method is called"
    0 * _

    cleanup:
    getUserModel = null
  }

  def "should get all users by first name"() {
    given: "a GetUserModel with firstName only"
    def getUserModel = new GetUserModel(_ as String, null)

    when: "getAllUsers method is called with provided GetUserModel"
    userService.getAllUsers(getUserModel)

    then: "findByFirstNameLike method from repository is called once with GetUserModel's firstName"
    1 * userRepository.findByFirstNameLike("%" + getUserModel.firstName + "%")

    then: "no other method is called"
    0 * _

    cleanup:
    getUserModel = null
  }

  def "should get all users by last name"() {
    given: "a GetUserModel with lastName only"
    def getUserModel = new GetUserModel(null, _ as String)

    when: "getAllUsers method is called with provided GetUserModel"
    userService.getAllUsers(getUserModel)

    then: "findByLastNameLike method from repository is called once with GetUserModel's firstName"
    1 * userRepository.findByLastNameLike("%" + getUserModel.lastName + "%")

    then: "no other method is called"
    0 * _

    cleanup:
    getUserModel = null
  }

  def "should get all users by first and last name"() {
    given: "a GetUserModel with firstName and lastName"
    def getUserModel = new GetUserModel(_ as String, _ as String)

    when: "getAllUsers method is called with provided GetUserModel"
    userService.getAllUsers(getUserModel)

    then: "findByFirstNameLikeAndLastNameLike method from repository is called once with GetUserModel's both parameters"
    1 * userRepository.findByFirstNameLikeAndLastNameLike("%" + getUserModel.firstName + "%",
                                                          "%" + getUserModel.lastName + "%")

    then: "no other method is called"
    0 * _

    cleanup:
    getUserModel = null
  }

  def "should successfully get user by Id"() {
    given: "a UUID"
    def uuid = UUID.randomUUID()
    and: "a User"
    def user = new User()

    when: "getUserById method is called with provided UUID"
    userService.getUserById(uuid)

    then: "findById method from repository is called once"
    1 * userRepository.findById(uuid) >> Optional.of(user)

    then: "no other method is called"
    0 * _

    cleanup:
    uuid = null
    user = null
  }

  def "should throw UserNotFoundException when provided ID does not exist in DB"() {
    given: "a UUID that does not exist in DB"
    def id = UUID.randomUUID()

    when: "getUserById method is called with provided UUID"
    userService.getUserById(id)

    then: "findById method from repository is called once but no user was found thus an empty optional was returned"
    1 * userRepository.findById(id) >> Optional.empty()

    then: "that results in a UserNotFoundException to be thrown"
    thrown(UserNotFoundException)

    then: "no other method is called"
    0 * _

    cleanup:
    id = null
  }

  def "should delete user"() {
    given: "a UUID"
    def uuid = UUID.randomUUID()

    when: "deleteUser method is called with provided UUID"
    userService.deleteUser(uuid)

    then: "deleteById from repository is called once with provided UUID"
    1 * userRepository.deleteById(uuid)

    then: "no other method is called"
    0 * _
  }

  def "should delete all users"() {
    when: "deleteAllUsers method is called with provided UUID"
    userService.deleteAllUsers()

    then: "deleteAll method from repository is called once"
    1 * userRepository.deleteAll()

    then: "no other method is called"
    0 * _
  }

  def "should update user"() {
    given: "an UpdateUserRequestDto"
    def updateUserRequestDto = new UpdateUserRequestDto(UUID.randomUUID(),
                                                        _ as String,
                                                        _ as String,
                                                        _ as String)
    and: "a user retrieved from DB"
    User retrievedUser = new User(updateUserRequestDto.userId,
                                 _ as String,
                                 _ as String,
                                 _ as String,
                                 LocalDateTime.now(),
                                 null)
    and: "a user representing the updated retrievedUser"
    def newUser = new User(updateUserRequestDto.userId,
                           updateUserRequestDto.firstName,
                           updateUserRequestDto.lastName,
                           updateUserRequestDto.email,
                           retrievedUser.createdAt,
                           null)

    when: "updateUser method is called with provided UpdateUserRequestDto"
    userService.updateUser(updateUserRequestDto)

    then: "validateEmail is called once to validate UpdateUserRequestDto's e-mail"
    1 * validator.validateEmail(updateUserRequestDto.email)

    then: "findById from repository is called once to retrieve the user to update"
    1 * userRepository.findById(updateUserRequestDto.userId) >> Optional.of(retrievedUser)

    then: "save from repository is called once to save newUser"
    1 * userRepository.save(newUser)

    then: "no other method is called"
    0 * _
  }

  def "should fail to update user because it does not exist"() {
    given: "an UpdateUserRequestDto"
    def updateUserRequestDto = new UpdateUserRequestDto(UUID.randomUUID(),
                                                        _ as String,
                                                        _ as String,
                                                        _ as String)

    when: "updateUser method is called with provided UpdateUserRequestDto"
    userService.updateUser(updateUserRequestDto)

    then: "the provided email is being validated"
    1 * validator.validateEmail(updateUserRequestDto.email)

    then: "findById is called once to retrieve the user but returns an empty optional"
    1 * userRepository.findById(updateUserRequestDto.userId) >> Optional.empty()

    then: "no other method is called"
    0 * _
    and:
    thrown(UserNotFoundException)
  }
}