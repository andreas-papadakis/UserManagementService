package com.agileactors.usermanagementservice.converter;

import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert String to UpdateUserRequestDtoConverter.
 * String must be comma separated with UUID first, first name second,
 * last name third and last the e-mail.
 */
@SuppressWarnings("checkstyle:LineLengthCheck")
@Component
public class StringToUpdateUserRequestDtoConverter implements Converter<String, UpdateUserRequestDto> {
  @Override
  public UpdateUserRequestDto convert(String from) {
    String[] data = from.split(",");

    if (data.length < 4) {
      throw new IllegalArgumentException("Missing argument in StringToUpdateUserRequestDtoConverter. Should be 4.");
    } else if (data.length > 5) {
      throw new IllegalArgumentException("More than expected arguments in StringToUpdateUserRequestDtoConverter. Should be 4.");
    } else if (!data[0].contains("-") || !data[3].contains("@")) {
      throw new IllegalArgumentException("Wrong order of arguments in StringToUpdateUserRequestDtoConverter. "
                                      +  "UUID first, first name second, last name third and last the e-mail.");
    }

    return new UpdateUserRequestDto(UUID.fromString(data[0]), data[1], data[2], data[3]);
  }
}