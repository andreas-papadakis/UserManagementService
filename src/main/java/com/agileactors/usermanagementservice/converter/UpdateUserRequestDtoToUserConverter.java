package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.model.User;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts {@link UpdateUserRequestDto} to {@link User}.
 */
@Component
public class UpdateUserRequestDtoToUserConverter implements Converter<UpdateUserRequestDto, User> {
  /**
   * Converts {@link UpdateUserRequestDto} to {@link User}.
   *
   * @param updateUserRequestDto The {@link UpdateUserRequestDto} to convert to {@link User}
   *
   * @return The {@link User}
   */
  @Override
  public User convert(UpdateUserRequestDto updateUserRequestDto) {
    return new User(UUID.randomUUID(),
                    updateUserRequestDto.firstName(),
                    updateUserRequestDto.lastName(),
                    updateUserRequestDto.email(),
                    null,
                    null);
  }
}
