package com.agileactors.usermanagementservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

/**
 * Exception to be returned to client.
 *
 * @param message Exception's message
 * @param httpStatus HTTP status to be returned to client
 * @param timestamp The time the error occurred
 */
public record ApiException(@Schema (name = "message",
                                    example = "User with ID 123e4567-e89b-12d3-a456-426614174000 "
                                            + "does not exist",
                                    description = "Message providing detailed info about occurred "
                                                + "error")
                           String message,
                           @Schema (name = "httpStatus",
                                   example = "404 NOT_FOUND",
                                   description = "The http status code of the occurred error",
                                   type = "string")
                           HttpStatus httpStatus,
                           @Schema (name = "timestamp",
                                   example = "2022-09-10T22:51:20.879Z",
                                   description = "The time the error occurred")
                           LocalDateTime timestamp) { }