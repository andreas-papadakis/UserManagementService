package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.FindUserResponseDto;
import com.agileactors.usermanagementservice.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert {@link com.agileactors.usermanagementservice.model.User} to
 * {@link FindUserResponseDto}.
 */
@Component
public class UserToFindUserResponseDtoConverter implements Converter<User, FindUserResponseDto> {
  /**
   * Convert {@link com.agileactors.usermanagementservice.model.User user} to
   * {@link FindUserResponseDto}.
   *
   * @param user The {@link com.agileactors.usermanagementservice.model.User} to convert to
   *             {@link FindUserResponseDto}
   *
   * @return The {@link FindUserResponseDto}
   */
  @Override
  public FindUserResponseDto convert(User user) {
    return new FindUserResponseDto(user.getFirstName(),
                                   user.getLastName(),
                                   user.getEmail());
  }
}