package com.agileactors.user_management_service.service;

import com.agileactors.user_management_service.model.User;

import java.util.List;
import java.util.Optional;

interface UserServiceInterface {
    /**
     * Upload user in DB
     * @param user The user to be uploaded
     * @return The uploaded user on success
     */
    User createUser(User user);

    /**
     * Retrieve all users from DB.
     * If first_name is not blank then retrieve all those with similar first name to first_name.
     * In order to be similar, a first name must have up to 3 characters before (case-insensitive),
     * then contain the first name as is and contain any other character and as many afterwards.
     * @param first_name The first name to retrieve users who have a similar
     * @return List with retrieved users
     */
    List<User> getAllUsers(String first_name);

    /**
     * Retrieve a specific user
     * @param user_id User's to be retrieved ID
     * @return Optional with user if one was retrieved or null otherwise
     */
    Optional<User> getUserById(Long user_id);

    /**
     * Remove user with ID user_id from DB if exists
     * @param user_id The ID of user to be deleted
     * @return Number of users removed from DB
     */
    int deleteUser(Long user_id);

    /**
     * Update user with ID user_id in DB with the values stored in user updated_user
     * @param user_id The ID of user to be updated
     * @param updated_user The user holding the new values
     * @return Optional with updated user if one was retrieved or null otherwise
     */
    Optional<User> updateUser(Long user_id, User updated_user);
}