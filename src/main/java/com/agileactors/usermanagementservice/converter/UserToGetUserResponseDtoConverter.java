package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.GetUserResponseDto;
import com.agileactors.usermanagementservice.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert {@link com.agileactors.usermanagementservice.model.User} to
 * {@link com.agileactors.usermanagementservice.dto.GetUserResponseDto}.
 */
@Component
public class UserToGetUserResponseDtoConverter implements Converter<User, GetUserResponseDto> {
  /**
   * Convert {@link com.agileactors.usermanagementservice.model.User} to
   * {@link com.agileactors.usermanagementservice.dto.GetUserResponseDto}.
   *
   * @param user The user to convert to dto
   *
   * @return The dto
   */
  @Override
  public GetUserResponseDto convert(User user) {
    return new GetUserResponseDto(user.getFirstName(),
                                  user.getLastName(),
                                  user.getEmail());
  }
}