package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.UpdateUserResponseDto;
import com.agileactors.usermanagementservice.model.User;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert User to UpdateUserResponseDto.
 */
@SuppressWarnings("checkstyle:LineLengthCheck")
@Component
public class UserToUpdateUserResponseDtoConverter implements Converter<User, UpdateUserResponseDto> {
  /**
   * Convert User to UpdateUserResponseDto.
   *
   * @param user The user to convert
   *
   * @return The UpdateUserResponseDto
   */
  @Override
  public UpdateUserResponseDto convert(User user) {
    return new UpdateUserResponseDto(UUID.fromString(user.getId()),
                                     user.getFirstName(),
                                     user.getLastName(),
                                     user.getEmail(),
                                     user.getCreatedAt(),
                                     user.getUpdatedAt());
  }
}