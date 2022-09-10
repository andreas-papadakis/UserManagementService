package com.agileactors.usermanagementservice.controller;

import com.agileactors.usermanagementservice.dto.*;

import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * Upload user in DB
     * @param createUserRequestDto The user to be uploaded
     * @return The uploaded user on success
     */
    @Operation(summary = "Create user",
               description = "Create a new user in DB. All fields must be filled and e-mail must contain '@' and a '.' afterwards.",
               tags = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "User successfully created",
                         content = { @Content (mediaType = "application/json",
                                               schema = @Schema(implementation = CreateUserResponseDto.class)) }
                        ),
            @ApiResponse(responseCode = "400",
                         description = "User was NOT created. Check again the fields. Neither field must be blank and e-mail must be in correct format",
                         content = @Content
                        )
            })
    @PostMapping(value = "/users")
    public CreateUserResponseDto createUser(@RequestBody @Valid CreateUserRequestDto createUserRequestDto) {
        return userService.createUser(createUserRequestDto);
    }

    /**
     * Retrieve all users from database whose first name contains the search_term.
     * @param search_term The term that first_name must contain to retrieve the user
     * @return List with retrieved users
     */
    @Operation(summary = "Get all users",
               description = "Retrieve all users from DB. If parameter firstName exists, retrieve those whose first name contains the given value.",
               tags = "GET")
    @ApiResponse(responseCode = "200",
                 description = "Return a list with retrieved users. If no users were retrieved, list will be empty",
                 content = { @Content (mediaType = "application/json",
                                       schema = @Schema (implementation = GetUserResponseDto.class) ) }
                )
    @GetMapping(value = "/users")
    public List<GetUserResponseDto> getAllUsers(@RequestParam(value = "firstName", defaultValue = "") String search_term) {
            return userService.getAllUsers(search_term);
    }

    /**
     * Retrieve a specific user
     * @param user_id User's to be retrieved ID
     * @return Optional with user if one was retrieved or null otherwise
     */
    @Operation(summary = "Get user by id",
               description = "Retrieve user with specific id",
               tags = "GET")
    @ApiResponse(responseCode = "200",
                 description = "Return retrieved user or null",
                 content = { @Content (mediaType = "application/json",
                                       schema = @Schema (implementation = GetUserResponseDto.class) ) }
                )
    @GetMapping(value = "/users/{id}")
    public Optional<GetUserResponseDto> getUserById(@PathVariable(value = "id") String user_id) {
        return userService.getUserById(user_id);
    }

    /**
     * Remove user with ID user_id from DB if exists
     * @param user_id The ID of user to be deleted
     * @return Number of users removed from DB
     */
    @Operation(summary = "Remove user",
               description = "Remove user with given id from DB",
               tags = "DELETE")
    @ApiResponse(responseCode = "200",
                 description = "Return number of users removed from DB",
                 content = { @Content (mediaType = "application/json",
                                       schema = @Schema (implementation = Integer.class) ) }
    )
    @DeleteMapping(value = "/users/{id}")
    public int deleteUser(@PathVariable(value = "id") String user_id) {
        return userService.deleteUser(user_id);
    }

    /**
     * Remove all users from DB
     * @return Number of users removed from DB
     */
    @Operation(summary = "Remove all users",
            description = "Remove all users",
            tags = "DELETE")
    @ApiResponse(responseCode = "200",
                 description = "Return number of users removed from DB",
                 content = { @Content (mediaType = "application/json",
                                       schema = @Schema (implementation = Integer.class) ) }
    )
    @DeleteMapping(value = "/users")
    public int deleteAllUsers() {
        return userService.deleteAllUsers();
    }

    /**
     * Update user with ID user_id in DB with the values stored in user updated_user
     * @param userId The ID of user to be updated
     * @param updateUserRequestDto The dto holding the new values
     * @return All the info of the updated user
     */
    @Operation(summary = "Update user",
               description = "Update a user's basic information",
               tags = "PUT")
    @ApiResponse(responseCode = "200",
                 description = "Return updated user or empty user if user id not found",
                 content = { @Content (mediaType = "application/json",
                                       schema = @Schema (implementation = CreateUserResponseDto.class) ) }
                )
    @PutMapping(value = "/users/{id}")
    public UpdateUserResponseDto updateUser(@PathVariable(value = "id") UUID userId,
                                            @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        UpdateUserRequestDto updatedUserDto = new UpdateUserRequestDto(userId,
                                                                       updateUserRequestDto.firstName(),
                                                                       updateUserRequestDto.lastName(),
                                                                       updateUserRequestDto.email());
        User updatedUser = userService.updateUser(updatedUserDto);
        return new UpdateUserResponseDto(updatedUser.getId(),
                                         updatedUser.getFirstName(),
                                         updatedUserDto.lastName(),
                                         updatedUser.getEmail(),
                                         updatedUser.getCreatedAt(),
                                         updatedUser.getUpdatedAt());
    }
}