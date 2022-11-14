package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import com.agileactors.usermanagementservice.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import spock.lang.Specification

@SpringBootTest
class CreateUserRequestDtoToUserConverterSpecification extends Specification {
  @Autowired
  private ConversionService conversionService

  def "should convert"() {
    given: "a CreateUserRequestDto"
    CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("firstName",
                                                                         "lastName",
                                                                         "a@a.com")

    when: "I convert it to User class"
    User user = conversionService.convert(createUserRequestDto, User.class)

    then: "the returned user has an id"
    user.id != null

    and: "converter copied the first name"
    user.firstName == createUserRequestDto.firstName

    and: "the last name"
    user.lastName == createUserRequestDto.lastName

    and: "the email"
    user.email == createUserRequestDto.email

    and: "assigned null at createdAt"
    user.createdAt == null

    and: "updatedAt variables"
    user.updatedAt == null
  }
}