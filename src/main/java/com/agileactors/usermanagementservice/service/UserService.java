package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUpdateUserRequestDto;
import com.agileactors.usermanagementservice.dto.CreateUpdateUserResponseDto;
import com.agileactors.usermanagementservice.dto.GetUserResponseDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Upload user in DB
     * @param user The user to be uploaded
     * @return The uploaded user on success
     */
    CreateUpdateUserResponseDto createUser(CreateUpdateUserRequestDto user);

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
     */
    Optional<GetUserResponseDto> getUserById(String user_id);

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
     * Update user with ID user_id in DB with the values stored in user updated_user
     * @param user_id The ID of user to be updated
     * @param updated_user The user holding the new values
     * @return All the info of the updated user if one was retrieved or an empty one otherwise
     */
    User updateUser(UpdateUserRequestDto updated_user);
}
