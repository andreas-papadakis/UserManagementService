package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.model.User
import java.time.LocalDateTime
import spock.lang.Specification
import spock.lang.Subject

class UserToUpdateUserResponseDtoConverterSpec extends Specification {
  @Subject
  def userToUpdateUserResponseDtoConverter = new UserToUpdateUserResponseDtoConverter()

  def "should convert User to UpdateUserResponseDto"() {
    given: "a User"
    def user = new User(UUID.randomUUID(),
                        _ as String,
                        _ as String,
                        _ as String,
                        LocalDateTime.now(),
                        LocalDateTime.now())

    when: "userToUpdateUserResponseDtoConverter converts user"
    def returnedUpdateUserResponseDto =
            userToUpdateUserResponseDtoConverter.convert(user)

    then: "returned UpdateUserResponseDto is as expected"
    returnedUpdateUserResponseDto.id()      == user.id
    returnedUpdateUserResponseDto.firstName == user.firstName
    returnedUpdateUserResponseDto.lastName  == user.lastName
    returnedUpdateUserResponseDto.email     == user.email
    returnedUpdateUserResponseDto.createdAt == user.createdAt
    returnedUpdateUserResponseDto.updatedAt == user.updatedAt
  }
}