package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.CreateUserResponseDto;
import com.agileactors.usermanagementservice.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts {@link com.agileactors.usermanagementservice.model.User} to
 * {@link com.agileactors.usermanagementservice.dto.CreateUserResponseDto}.
 */
@Component
public class UserToCreateUserResponseDtoConverter
       implements Converter<User, CreateUserResponseDto> {
  /**
   * Converts {@link com.agileactors.usermanagementservice.model.User} to
   * {@link com.agileactors.usermanagementservice.dto.CreateUserResponseDto}.
   *
   * @param user The user to convert to dto
   *
   * @return The dto
   */
  @Override
  public CreateUserResponseDto convert(User user) {
    return new CreateUserResponseDto(user.getId(),
                                     user.getFirstName(),
                                     user.getLastName(),
                                     user.getEmail(),
                                     user.getCreatedAt(),
                                     user.getUpdatedAt());
  }
}