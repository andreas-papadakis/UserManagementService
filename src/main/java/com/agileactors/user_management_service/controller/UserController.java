package com.agileactors.user_management_service.controller;

import com.agileactors.user_management_service.model.User;
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
                                               schema = @Schema(implementation = User.class)) }
                        ),
            @ApiResponse(responseCode = "400",
                         description = "User was NOT created. Check again the fields. Neither field must be blank and e-mail must be in correct format",
                         content = @Content
                        )
            })
    @PostMapping(value = "/users")
    public User createUser(@RequestBody @Valid User user) {
        return userServiceImpl.createUser(user);
    }

    /**
     * Retrieve all users from DB.
     * If first_name is not blank then retrieve all those with similar first name to first_name.
     * A name is similar if it starts with up to 3 characters before given value (case-insensitive),
     * then contains the value as is and can continue with any and as many characters afterwards.
     * @param first_name The first name to retrieve users who have a similar
     * @return List with retrieved users
     */
    @Operation(summary = "Get all users",
               description = "Retrieve all users from DB. If parameter firstName exists, retrieve those whose first name matches the given value. " +
                             "A name matches if it starts with up to 3 characters before given value (case-insensitive), " +
                             "then contains the value as is and can continue with any and as many characters afterwards.",
               tags = "GET")
    @ApiResponse(responseCode = "200",
                 description = "Return a list with retrieved users. If no users were retrieved, list will be empty",
                 content = { @Content (mediaType = "application/json",
                                       schema = @Schema (implementation = User.class) ) }
                )
    @GetMapping(value = "/users")
    public List<User> getAllUsers(@RequestParam(value = "firstName", defaultValue = "") String first_name) {
            return userServiceImpl.getAllUsers(first_name);
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
                                       schema = @Schema (implementation = User.class) ) }
                )
    @GetMapping(value = "/users/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Long user_id) {
        return userServiceImpl.getUserById(user_id);
    }

    /**
     * Update user with ID user_id in DB with the values stored in user updated_user
     * @param user_id The ID of user to be updated
     * @param updated_user The user holding the new values
     * @return Optional with the updated user if ID exists or null otherwise
     */
    @Operation(summary = "Update user",
               description = "Update a user's basic information",
               tags = "PUT")
    @ApiResponse(responseCode = "200",
                 description = "Return updated user or null if user id not found",
                 content = { @Content (mediaType = "application/json",
                                       schema = @Schema (implementation = User.class) ) }
                )
    @PutMapping(value = "/users/{id}")
    public Optional<User> updateUser(@PathVariable(value = "id") Long user_id, @RequestBody @Valid User updated_user) {
        return userServiceImpl.updateUser(user_id, updated_user);
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
                                       schema = @Schema (implementation = User.class) ) }
                 )
    @DeleteMapping(value = "/users/{id}")
    public int deleteUser(@PathVariable(value = "id") Long user_id) {
        return userServiceImpl.deleteUser(user_id);
    }
}