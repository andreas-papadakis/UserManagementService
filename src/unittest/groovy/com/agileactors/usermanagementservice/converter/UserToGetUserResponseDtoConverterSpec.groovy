package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.model.User
import spock.lang.Specification
import spock.lang.Subject

class UserToGetUserResponseDtoConverterSpec extends Specification {
  @Subject
  def userToGetUserResponseDtoConverter = new UserToGetUserResponseDtoConverter()

  def "should convert User to GetUserResponseDto"() {
    given: "a User"
    def user = new User(UUID.randomUUID(),
                        _ as String,
                        _ as String,
                        _ as String,
                        null,
                        null)

    when: "userToGetUserResponseDtoConverter converts user"
    def returnedGetUserResponseDto = userToGetUserResponseDtoConverter.convert(user)

    then: "returned GetUserResponseDto is as expected"
    returnedGetUserResponseDto.firstName == user.firstName
    returnedGetUserResponseDto.lastName  == user.lastName
    returnedGetUserResponseDto.email     == user.email
  }
}