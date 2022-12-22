package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.model.User
import spock.lang.Specification
import spock.lang.Subject

class UserToFindUserResponseDtoConverterSpec extends Specification {
  @Subject
  def userToFindUserResponseDtoConverter = new UserToFindUserResponseDtoConverter()

  def "should convert User to GetUserResponseDto"() {
    given: "a User"
    def user = new User(UUID.randomUUID(),
                        _ as String,
                        _ as String,
                        _ as String,
                        null,
                        null)

    when: "userToGetUserResponseDtoConverter converts user"
    def returnedFindUserResponseDto = userToFindUserResponseDtoConverter.convert(user)

    then: "returned GetUserResponseDto is as expected"
    returnedFindUserResponseDto.firstName == user.firstName
    returnedFindUserResponseDto.lastName  == user.lastName
    returnedFindUserResponseDto.email     == user.email
  }
}