package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.CreateUserResponseDto;
import com.agileactors.usermanagementservice.dto.GetUserResponseDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.validation.BindingResult;

/**
 * Interface to connect the controller with the repository.
 */
@SuppressWarnings("checkstyle:LineLengthCheck")
public interface UserService {
  /**
   * Upload user in DB.
   *
   * @param createUserRequestDto The user to be uploaded
   *
   * @param errors The validation errors of createUserRequestDto
   *
   * @return The uploaded user on success
   */
  CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto, BindingResult errors);

  /**
   * Retrieve all users from database. If searchTerm is not blank retrieve those whose first name contains the searchTerm.
   *
   * @param searchTerm The term that user's first name must contain to be retrieved
   *
   * @return List with retrieved users
   */
  List<GetUserResponseDto> getAllUsers(String searchTerm);

  /**
   * Retrieve a specific user.
   *
   * @param userId User's to be retrieved ID
   *
   * @return Retrieved user
   *
   * @throws com.agileactors.usermanagementservice.exception.UserNotFoundException When user not found
   */
  GetUserResponseDto getUserById(UUID userId);

  /**
   * Remove user with userId from DB.
   *
   * @param userId The ID of user to be deleted
   *
   * @return Number of users removed from DB
   */
  int deleteUser(String userId);

  /**
   * Remove all users.
   *
   * @return Number of users removed from DB
   */
  int deleteAllUsers();

  /**
   * Update user in DB.
   *
   * @param updatedUser The dto containing the ID of user to update and the new values
   *
   * @param errors The validation errors of createUserRequestDto
   *
   * @return The updated user
   *
   * @throws com.agileactors.usermanagementservice.exception.UserNotFoundException When user not found
   */
  User updateUser(UpdateUserRequestDto updatedUser, BindingResult errors);
}