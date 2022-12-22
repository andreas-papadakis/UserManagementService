package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.SaveUserRequestDto;
import com.agileactors.usermanagementservice.model.User;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts {@link SaveUserRequestDto} to
 * {@link com.agileactors.usermanagementservice.model.User}.
 */
@Component
public class SaveUserRequestDtoToUserConverter
       implements Converter<SaveUserRequestDto, User> {
  /**
   * Converts {@link SaveUserRequestDto} to
   * {@link com.agileactors.usermanagementservice.model.User User}.
   *
   * @param saveUserRequestDto The {@link SaveUserRequestDto} to convert to
   *        {@link com.agileactors.usermanagementservice.model.User}
   *
   * @return The {@link com.agileactors.usermanagementservice.model.User}
   */
  @Override
  public User convert(SaveUserRequestDto saveUserRequestDto) {
    return new User(UUID.randomUUID(),
                    saveUserRequestDto.firstName(),
                    saveUserRequestDto.lastName(),
                    saveUserRequestDto.email(),
                    null,
                    null);
  }
}
