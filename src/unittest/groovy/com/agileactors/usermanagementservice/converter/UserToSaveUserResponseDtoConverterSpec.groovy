package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.model.User
import java.time.LocalDateTime
import spock.lang.Specification
import spock.lang.Subject

class UserToSaveUserResponseDtoConverterSpec extends Specification {
  @Subject
  def userToSaveUserResponseDtoConverter = new UserToSaveUserResponseDtoConverter()

  def "should convert User to SaveUserResponseDto"() {
    given: "a User"
    def user = new User(UUID.randomUUID(),
                        _ as String,
                        _ as String,
                        _ as String,
                        LocalDateTime.now(),
                        LocalDateTime.now())

    when: "userToCreateUserResponseDtoConverter converts user"
    def returnedSaveUserResponseDto =
            userToSaveUserResponseDtoConverter.convert(user)

    then: "returned CreateUserResponseDto is as expected"
    returnedSaveUserResponseDto.id        == user.id
    returnedSaveUserResponseDto.firstName == user.firstName
    returnedSaveUserResponseDto.lastName  == user.lastName
    returnedSaveUserResponseDto.email     == user.email
    returnedSaveUserResponseDto.createdAt == user.createdAt
    returnedSaveUserResponseDto.updatedAt == user.updatedAt
  }
}