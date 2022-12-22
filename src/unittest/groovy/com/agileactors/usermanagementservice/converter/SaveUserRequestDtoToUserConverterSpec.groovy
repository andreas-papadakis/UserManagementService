package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.dto.SaveUserRequestDto
import spock.lang.Specification
import spock.lang.Subject

class SaveUserRequestDtoToUserConverterSpec extends Specification {
  @Subject
  def saveUserRequestDtoToUserConverter = new SaveUserRequestDtoToUserConverter()

  def "should convert SaveUserRequestDto to User"() {
    given: "a SaveUserRequestDto"
    def saveUserRequestDto = new SaveUserRequestDto(_ as String, _ as String, _ as String)

    when: "saveUserRequestDtoToUserConverter converts SaveUserRequestDto"
    def returnedUser = saveUserRequestDtoToUserConverter.convert(saveUserRequestDto)

    then: "returned user is as expected"
    returnedUser.firstName == saveUserRequestDto.firstName
    returnedUser.lastName  == saveUserRequestDto.lastName
    returnedUser.email     == saveUserRequestDto.email
    returnedUser.createdAt == null
    returnedUser.updatedAt == null
  }
}