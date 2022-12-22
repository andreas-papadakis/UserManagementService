package com.agileactors.usermanagementservice.controller

import com.agileactors.usermanagementservice.dto.SaveUserRequestDto
import com.agileactors.usermanagementservice.dto.SaveUserResponseDto
import com.agileactors.usermanagementservice.dto.FindUserResponseDto
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto
import com.agileactors.usermanagementservice.dto.UpdateUserResponseDto
import com.agileactors.usermanagementservice.exception.InvalidArgumentException
import com.agileactors.usermanagementservice.model.GetUserModel
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.service.UserService
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.ObjectError
import org.springframework.core.convert.ConversionService
import spock.lang.Specification
import spock.lang.Subject

class UserControllerSpec extends Specification {
  private def userService       = Mock(UserService)
  private def conversionService = Mock(ConversionService)
  @Subject
  private def userController    = new UserController(userService, conversionService)

  def "should save a User"() {
    given: "a valid SaveUserRequestDto"
    def saveUserRequestDto = new SaveUserRequestDto(_ as String,
                                                    _ as String,
                                                    _ as String + ' ') //needs an extra char
                                                                       //because _ as String is
                                                                       //4 chars long and min is
                                                                       //5 for javax validation
    and: "a User"
    def user = new User()
    and: "a BindingResult on that User"
    def beanPropertyBindingResult = new BeanPropertyBindingResult(saveUserRequestDto,
                                                                  "saveUserRequestDto")

    when: "save method is called"
    userController.save(saveUserRequestDto, beanPropertyBindingResult)

    then: "save from service layer gets called once and returns a User"
    1 * userService.save(_ as SaveUserRequestDto) >> user

    then: "controller converts the returned user to SaveUserResponseDto"
    1 * conversionService.convert(user, SaveUserResponseDto.class)

    then: "no other method gets called"
    0 * _

    cleanup:
    saveUserRequestDto  = null
    user                  = null
  }

  def "if BindingResult parameter has errors, save method should throw an exception"() {
    given: "an invalid SaveUserRequestDto"
    def saveUserRequestDto = new SaveUserRequestDto("", "", "")
    and: "a BindingResult on that SaveUserRequestDto containing some random errors"
    def beanPropertyBindingResult = new BeanPropertyBindingResult(saveUserRequestDto,
                                                                  "saveUserRequestDto")
    def objectError1 = new ObjectError("createUserRequestDto",
                                       "error message 1")
    def objectError2 = new ObjectError("createUserRequestDto",
                                       "error message 2")
    def objectError3 = new ObjectError("createUserRequestDto",
                                       "error message 3")
    beanPropertyBindingResult.addError(objectError1)
    beanPropertyBindingResult.addError(objectError2)
    beanPropertyBindingResult.addError(objectError3)

    when: "save is called"
    userController.save(saveUserRequestDto, beanPropertyBindingResult)

    then: "no method is called"
    0 * _
    and: "an InvalidArgumentException was thrown"
    def invalidArgumentException = thrown InvalidArgumentException
    and: "the exception message is as expected"
    invalidArgumentException.message == objectError1.defaultMessage + " " +
                                        objectError2.defaultMessage + " " +
                                        objectError3.defaultMessage + " "

    cleanup:
    saveUserRequestDto        = null
    beanPropertyBindingResult = null
    objectError1              = null
    objectError2              = null
    objectError3              = null
    invalidArgumentException  = null
  }

  def "should get all users"() {
    given: "a GetUserModel"
    def getUserModel = new GetUserModel(_ as String, _ as String)
    and: "a list containing some users"
    def usersList = new ArrayList<User>(List.of(new User(), new User(), new User()))

    when: "find is called"
    userController.find(getUserModel)

    then: "find from service gets called once and returns a list with retrieved user(s)"
    1 * userService.find(getUserModel) >> usersList

    then: "convert method converts usersList's users to FindUserResponseDto"
    usersList.each {user ->
      1 * conversionService.convert(user, FindUserResponseDto.class)
    }

    then: "no other method gets called"
    0 * _

    cleanup:
    getUserModel             = null
    usersList                = null
  }

  def "should get user by ID"() {
    given: "a UUID"
    def id = UUID.randomUUID()
    and: "a User"
    def user = new User()

    when: "getUserById is called"
    userController.findById(id)

    then: "getUserById gets called once with the provided id as parameter and returns the retrieved user"
    1 * userService.findById(id) >> user

    then: "conversion service converts the retrieved user to GetUserResponseDto"
    1 * conversionService.convert(user, FindUserResponseDto.class)

    then: "no other method gets called"
    0 * _

    cleanup:
    id                 = null
    user               = null
  }

  def "should delete user by id"() {
    given: "a UUID"
    def id = UUID.randomUUID()

    when: "deleteUser is called"
    userController.deleteById(id)

    then: "deleteUser from service is called once with the provided id as parameter"
    1 * userService.deleteById(id)

    then: "no other method is called"
    0 * _

    cleanup:
    id = null
  }

  def "should delete all users"() {
    when: "deleteAll is called"
    userController.deleteAll()

    then: "deleteAll from service is called once"
    1 * userService.deleteAll()

    then: "no other method is called"
    0 * _
  }

  def "should update the user"() {
    given: "a UUID"
    def id = UUID.randomUUID()
    and: "an UpdateUserRequestDto"
    def updateUserRequestDto = new UpdateUserRequestDto(id,
                                                        _ as String,
                                                        _ as String,
                                                        _ as String + " ") //needs an extra char
                                                                           //because _ as String is
                                                                           //4 chars long and min is
                                                                           //5 for javax validation
    and: "a User"
    def user = new User()
    and: "a BindingResult on that User"
    def beanPropertyBindingResult = new BeanPropertyBindingResult(updateUserRequestDto,
                                                                  "updateUserRequestDto")

    when: "updateById is called"
    userController.updateById(id, updateUserRequestDto, beanPropertyBindingResult)

    then: "updateById from service is called once and returns a user"
    1 * userService.updateById(updateUserRequestDto) >> user

    then: "convert is called once to convert the returned user to UpdateUserResponseDto"
    1 * conversionService.convert(user, UpdateUserResponseDto.class)

    then: "no other method is called"
    0 * _

    cleanup:
    id                        = null
    updateUserRequestDto      = null
    user                      = null
    beanPropertyBindingResult = null
  }

  def "should fail to update user because provided data are invalid"() {
    given: "a UUID"
    def id = UUID.randomUUID()
    and: "an invalid UpdateUserRequestDto"
    def updateUserRequestDto = new UpdateUserRequestDto(id, "", "", "")
    and: "a BindingResult on that UpdateUserRequestDto containing some random errors"
    def beanPropertyBindingResult = new BeanPropertyBindingResult(UpdateUserRequestDto,
                                                                  "updateUserRequestDto")

    def objectError1 = new ObjectError("createUserRequestDto",
                                       "error message 1")
    def objectError2 = new ObjectError("createUserRequestDto",
                                       "error message 2")
    def objectError3 = new ObjectError("createUserRequestDto",
                                       "error message 3")

    beanPropertyBindingResult.addError(objectError1)
    beanPropertyBindingResult.addError(objectError2)
    beanPropertyBindingResult.addError(objectError3)

    when: "updateById method is called"
    userController.updateById(id, updateUserRequestDto, beanPropertyBindingResult)

    then: "no other method is called"
    0 * _
    and: "an InvalidArgumentException was thrown"
    def invalidArgumentException = thrown InvalidArgumentException
    and: "the exception message is as expected"
    invalidArgumentException.message == objectError1.defaultMessage + " " +
                                        objectError2.defaultMessage + " " +
                                        objectError3.defaultMessage + " "
  }
}