package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.model.User
import java.time.LocalDateTime
import spock.lang.Specification
import spock.lang.Subject

class UserToCreateUserResponseDtoConverterSpec extends Specification {
  @Subject
  def userToCreateUserResponseDtoConverter = new UserToCreateUserResponseDtoConverter()

  def "should convert User to CreateUserResponseDto"() {
    given: "a User"
    def user = new User(UUID.randomUUID(),
                        _ as String,
                        _ as String,
                        _ as String,
                        LocalDateTime.now(),
                        LocalDateTime.now())

    when: "userToCreateUserResponseDtoConverter converts user"
    def returnedCreateUserResponseDto =
            userToCreateUserResponseDtoConverter.convert(user)

    then: "returned CreateUserResponseDto is as expected"
    returnedCreateUserResponseDto.id        == user.id
    returnedCreateUserResponseDto.firstName == user.firstName
    returnedCreateUserResponseDto.lastName  == user.lastName
    returnedCreateUserResponseDto.email     == user.email
    returnedCreateUserResponseDto.createdAt == user.createdAt
    returnedCreateUserResponseDto.updatedAt == user.updatedAt
  }
}