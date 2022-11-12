package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.dto.CreateUserResponseDto
import com.agileactors.usermanagementservice.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class UserToCreateUserResponseDtoConverterSpecification extends Specification {
  @Autowired
  private ConversionService conversionService

  def "should convert"() {
    given: "a User"
    User user = new User(UUID.randomUUID(),
                         "firstName",
                         "lastName",
                         "a@a.com",
                         LocalDateTime.now(),
                         LocalDateTime.now())

    when: "I convert the user to CreateUserResponseDto"
    CreateUserResponseDto createUserResponseDto = conversionService.convert(
                                                                  user, CreateUserResponseDto.class)

    then: "all data are copied"
    createUserResponseDto.id        == user.id
    createUserResponseDto.firstName == user.firstName
    createUserResponseDto.lastName  == user.lastName
    createUserResponseDto.email     == user.email
    createUserResponseDto.createdAt == user.createdAt
    createUserResponseDto.updatedAt == user.updatedAt
  }
}