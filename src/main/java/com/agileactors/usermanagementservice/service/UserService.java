package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.CreateUserResponseDto;
import com.agileactors.usermanagementservice.dto.GetUserResponseDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.model.User;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    /**
     * Upload user in DB
     * @param createUserRequestDto The user to be uploaded
     * @param errors The validation errors of createUserRequestDto
     * @return The uploaded user on success
     */
    CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto, BindingResult errors);

    /**
     * Retrieve all users from database whose first name contains the search_term.
     * @param search_term The term that first_name must contain to retrieve the user
     * @return List with retrieved users
     */
    List<GetUserResponseDto> getAllUsers(String search_term);

    /**
     * Retrieve a specific user
     * @param user_id User's to be retrieved ID
     * @return Optional with user if one was retrieved or null otherwise
     * @throws com.agileactors.usermanagementservice.exception.UserNotFoundException When user not found
     */
    GetUserResponseDto getUserById(UUID user_id);

    /**
     * Remove user with ID user_id from DB if exists
     * @param user_id The ID of user to be deleted
     * @return Number of users removed from DB
     */
    int deleteUser(String user_id);

    /**
     * Remove all users
     * @return Number of users removed from DB
     */
    int deleteAllUsers();

    /**
     * Update user in DB
     * @param updated_user The dto containing the ID of user to update and the new values
     * @return The updated user
     * @throws com.agileactors.usermanagementservice.exception.UserNotFoundException When user not found
     */
    User updateUser(UpdateUserRequestDto updated_user);
}
