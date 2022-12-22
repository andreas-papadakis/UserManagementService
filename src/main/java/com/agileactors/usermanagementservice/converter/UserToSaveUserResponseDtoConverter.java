package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.SaveUserResponseDto;
import com.agileactors.usermanagementservice.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts {@link com.agileactors.usermanagementservice.model.User} to
 * {@link SaveUserResponseDto}.
 */
@Component
public class UserToSaveUserResponseDtoConverter
       implements Converter<User, SaveUserResponseDto> {
  /**
   * Converts {@link com.agileactors.usermanagementservice.model.User user} to
   * {@link SaveUserResponseDto}.
   *
   * @param user The {@link com.agileactors.usermanagementservice.model.User} to convert to
   *         {@link SaveUserResponseDto}
   *
   * @return The {@link SaveUserResponseDto}
   */
  @Override
  public SaveUserResponseDto convert(User user) {
    return new SaveUserResponseDto(user.getId(),
                                     user.getFirstName(),
                                     user.getLastName(),
                                     user.getEmail(),
                                     user.getCreatedAt(),
                                     user.getUpdatedAt());
  }
}