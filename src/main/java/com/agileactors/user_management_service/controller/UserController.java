package com.agileactors.user_management_service.controller;

import com.agileactors.user_management_service.dto.CreateUpdateUserRequestDTO;
import com.agileactors.user_management_service.dto.CreateUpdateUserResponseDTO;
import com.agileactors.user_management_service.dto.GetUserResponseDTO;
import com.agileactors.user_management_service.service.UserServiceImpl;

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

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;

    /**
     * Upload user in DB
     * @param user The user to be uploaded
     * @return The uploaded user on success
     */
    @Operation(summary = "Create user",
               description = "Create a new user in DB. All fields must be filled and e-mail must contain '@' and a '.' afterwards.",
               tags = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "User successfully created",
                         content = { @Content (mediaType = "application/json",
                                               schema = @Schema(implementation = CreateUpdateUserResponseDTO.class)) }
                        ),
            @ApiResponse(responseCode = "400",
                         description = "User was NOT created. Check again the fields. Neither field must be blank and e-mail must be in correct format",
                         content = @Content
                        )
            })
    @PostMapping(value = "/users")
    public CreateUpdateUserResponseDTO createUser(@RequestBody @Valid CreateUpdateUserRequestDTO user) {
        return userServiceImpl.createUser(user);
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
                                       schema = @Schema (implementation = GetUserResponseDTO.class) ) }
                )
    @GetMapping(value = "/users")
    public List<GetUserResponseDTO> getAllUsers(@RequestParam(value = "firstName", defaultValue = "") String search_term) {
            return userServiceImpl.getAllUsers(search_term);
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
                                       schema = @Schema (implementation = GetUserResponseDTO.class) ) }
                )
    @GetMapping(value = "/users/{id}")
    public Optional<GetUserResponseDTO> getUserById(@PathVariable(value = "id") String user_id) {
        return userServiceImpl.getUserById(user_id);
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
        return userServiceImpl.deleteUser(user_id);
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
        return userServiceImpl.deleteAllUsers();
    }

    /**
     * Update user with ID user_id in DB with the values stored in user updated_user
     * @param user_id The ID of user to be updated
     * @param updated_user The user holding the new values
     * @return All the info of the updated user if one was retrieved or an empty one otherwise
     */
    @Operation(summary = "Update user",
               description = "Update a user's basic information",
               tags = "PUT")
    @ApiResponse(responseCode = "200",
                 description = "Return updated user or empty user if user id not found",
                 content = { @Content (mediaType = "application/json",
                                       schema = @Schema (implementation = CreateUpdateUserResponseDTO.class) ) }
                )
    @PutMapping(value = "/users/{id}")
    public CreateUpdateUserResponseDTO updateUser(@PathVariable(value = "id") String user_id,
                                                            @RequestBody @Valid CreateUpdateUserRequestDTO updated_user) {
        return userServiceImpl.updateUser(user_id, updated_user);
    }
}