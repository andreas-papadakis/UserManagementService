package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.dto.GetUserResponseDto
import com.agileactors.usermanagementservice.model.User
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import spock.lang.Specification

@SpringBootTest
class UserToGetUserResponseDtoConverterSpecification extends Specification {
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

    when: "I convert the user to GetUserResponseDto"
    GetUserResponseDto getUserResponseDto = conversionService.convert(user,
                                                                      GetUserResponseDto.class)

    then: "first name is copied"
    getUserResponseDto.firstName == user.firstName

    and: "last name is copied"
    getUserResponseDto.lastName == user.lastName

    and: "email is copied"
    getUserResponseDto.email == user.email
  }
}