package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.UpdateUserResponseDto;
import com.agileactors.usermanagementservice.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert {@link com.agileactors.usermanagementservice.model.User} to
 * {@link com.agileactors.usermanagementservice.dto.UpdateUserResponseDto}.
 */
@Component
public class UserToUpdateUserResponseDtoConverter
       implements Converter<User, UpdateUserResponseDto> {
  /**
   * Convert {@link com.agileactors.usermanagementservice.model.User user} to
   * {@link com.agileactors.usermanagementservice.dto.UpdateUserResponseDto}.
   *
   * @param user The {@link com.agileactors.usermanagementservice.model.User} to convert to
   *             {@link com.agileactors.usermanagementservice.dto.UpdateUserResponseDto}
   *
   * @return The {@link com.agileactors.usermanagementservice.dto.UpdateUserResponseDto}
   */
  @Override
  public UpdateUserResponseDto convert(User user) {
    return new UpdateUserResponseDto(user.getId(),
                                     user.getFirstName(),
                                     user.getLastName(),
                                     user.getEmail(),
                                     user.getCreatedAt(),
                                     user.getUpdatedAt());
  }
}