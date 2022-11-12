package com.agileactors.usermanagementservice.converter

import com.agileactors.usermanagementservice.dto.UpdateUserResponseDto
import com.agileactors.usermanagementservice.model.User
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import spock.lang.Specification

@SpringBootTest
class UserToUpdateUserResponseDtoConverterSpecification extends Specification {
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

    when: "I convert the user to UpdateUserResponseDto"
    UpdateUserResponseDto updateUserResponseDto = conversionService.convert(user,
                                                                        UpdateUserResponseDto.class)

    then: "all data are copied"
    updateUserResponseDto.id        == user.id
    updateUserResponseDto.firstName == user.firstName
    updateUserResponseDto.lastName  == user.lastName
    updateUserResponseDto.email     == user.email
    updateUserResponseDto.createdAt == user.createdAt
    updateUserResponseDto.updatedAt == user.updatedAt
  }
}