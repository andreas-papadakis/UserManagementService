package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.model.User;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts {@link com.agileactors.usermanagementservice.dto.CreateUserRequestDto} to
 * {@link com.agileactors.usermanagementservice.model.User}.
 */
@Component
public class CreateUserRequestDtoToUserConverter
       implements Converter<CreateUserRequestDto, User> {
  /**
   * Converts {@link CreateUserRequestDto} to
   * {@link com.agileactors.usermanagementservice.model.User User}.
   *
   * @param createUserRequestDto The {@link CreateUserRequestDto} to convert to
   *         {@link com.agileactors.usermanagementservice.model.User}
   *
   * @return The {@link com.agileactors.usermanagementservice.model.User}
   */
  @Override
  public User convert(CreateUserRequestDto createUserRequestDto) {
    return new User(UUID.randomUUID(),
                    createUserRequestDto.firstName(),
                    createUserRequestDto.lastName(),
                    createUserRequestDto.email(),
                    null,
                    null);
  }
}
