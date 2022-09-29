package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.exception.UserNotFoundException;
import com.agileactors.usermanagementservice.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.validation.BindingResult;

/**
 * Interface to connect the
 * {@link com.agileactors.usermanagementservice.controller.UserController controller} with the
 * {@link com.agileactors.usermanagementservice.repository.UserRepository repository}.
 */
public interface UserService {
  /**
   * Creates new {@link com.agileactors.usermanagementservice.model.User user}.
   *
   * @param createUserRequestDto The {@link com.agileactors.usermanagementservice.model.User user}
   *                             to be uploaded
   * @param errors The validation errors of
   *     {@link com.agileactors.usermanagementservice.dto.CreateUserRequestDto createUserRequestDto}
   *
   * @return The created {@link com.agileactors.usermanagementservice.model.User user} on success
   */
  User createUser(CreateUserRequestDto createUserRequestDto, BindingResult errors);

  /**
   * Retrieves all {@link com.agileactors.usermanagementservice.model.User users} from database.
   * If searchTerm is not blank, retrieves those whose first name contains the searchTerm.
   *
   * @param searchTerm The term that {@link com.agileactors.usermanagementservice.model.User user's}
   *     first name must contain to be retrieved
   *
   * @return List with retrieved {@link com.agileactors.usermanagementservice.model.User users}
   */
  List<User> getAllUsers(String searchTerm);

  /**
   * Retrieves a specific {@link com.agileactors.usermanagementservice.model.User user}.
   *
   * @param userId {@link com.agileactors.usermanagementservice.model.User User's} to be retrieved
   *               ID
   *
   * @return Retrieved {@link com.agileactors.usermanagementservice.model.User user}
   *
   * @throws com.agileactors.usermanagementservice.exception.UserNotFoundException When user not
   *     found
   */
  User getUserById(UUID userId) throws UserNotFoundException;

  /**
   * Removes {@link com.agileactors.usermanagementservice.model.User user} with id userId.
   *
   * @param userId The ID of {@link com.agileactors.usermanagementservice.model.User user} to be
   *               removed
   *
   * @return Number of {@link com.agileactors.usermanagementservice.model.User users} removed
   */
  int deleteUser(UUID userId);

  /**
   * Removes all {@link com.agileactors.usermanagementservice.model.User users}.
   *
   * @return Number of {@link com.agileactors.usermanagementservice.model.User users} removed
   */
  int deleteAllUsers();

  /**
   * Updates {@link com.agileactors.usermanagementservice.model.User user}.
   *
   * @param updatedUser The dto containing the ID of
   *                    {@link com.agileactors.usermanagementservice.model.User user} to update and
   *                    the new values
   * @param errors The validation errors of
   *     {@link com.agileactors.usermanagementservice.dto.CreateUserRequestDto createUserRequestDto}
   *
   * @return The updated {@link com.agileactors.usermanagementservice.model.User user}
   *
   * @throws com.agileactors.usermanagementservice.exception.UserNotFoundException When user not
   *                                                                               found
   */
  User updateUser(UpdateUserRequestDto updatedUser, BindingResult errors)
          throws UserNotFoundException;
}