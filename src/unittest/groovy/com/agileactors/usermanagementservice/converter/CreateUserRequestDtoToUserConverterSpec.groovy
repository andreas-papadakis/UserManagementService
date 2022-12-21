package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import spock.lang.Specification
import spock.lang.Subject

class CreateUserRequestDtoToUserConverterSpec extends Specification {
  @Subject
  def createUserRequestDtoToUserConverter = new CreateUserRequestDtoToUserConverter()

  def "should convert CreateUserRequestDto to User"() {
    given: "a CreateUserRequestDto"
    def createUserRequestDto = new CreateUserRequestDto(_ as String, _ as String, _ as String)

    when: "createUserRequestDtoToUserConverter converts createUserRequestDto"
    def returnedUser = createUserRequestDtoToUserConverter.convert(createUserRequestDto)

    then: "returned user is as expected"
    returnedUser.firstName == createUserRequestDto.firstName
    returnedUser.lastName  == createUserRequestDto.lastName
    returnedUser.email     == createUserRequestDto.email
    returnedUser.createdAt == null
    returnedUser.updatedAt == null
  }
}