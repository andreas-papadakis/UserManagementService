package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.CreateUserResponseDto;
import com.agileactors.usermanagementservice.dto.GetUserResponseDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.exception.InvalidArgumentException;
import com.agileactors.usermanagementservice.exception.UserNotFoundException;
import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@SuppressWarnings("checkstyle:LineLengthCheck")
@Service
class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(CreateUserRequestDto createUserRequestDto, BindingResult errors) {
    if (errors.hasErrors()) {
      throw new InvalidArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
    }
    if (!createUserRequestDto.email().matches("[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+")) {
      throw new InvalidArgumentException("Invalid email.");
    }
    return userRepository.save(new User(UUID.randomUUID().toString(),
                                        createUserRequestDto.firstName(),
                                        createUserRequestDto.lastName(),
                                        createUserRequestDto.email(),
                                        null,
                                        null));
  }

  public List<User> getAllUsers(String searchTerm) {
    return (searchTerm.isBlank())
            ? userRepository.findAll()
                            .stream()
                            .toList()
            : userRepository.findByFirstNameLike("%" + searchTerm + "%")
                            .stream()
                            .toList();
  }

  public User getUserById(UUID userId) {
    return userRepository.findById(userId.toString())
                                       .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " does not exist"));
  }

  public int deleteUser(String userId) {
    return userRepository.deleteUserById(userId);
  }

  public int deleteAllUsers() {
    return userRepository.deleteAllUsers();
  }

  public User updateUser(UpdateUserRequestDto updateUserRequestDto, BindingResult errors) {
    if (errors.hasErrors()) {
      throw new InvalidArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
    }
    if (!updateUserRequestDto.email().matches("[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+")) {
      throw new InvalidArgumentException("Invalid email.");
    }
    User existingUser = userRepository.findById(updateUserRequestDto.userId().toString())
                                      .orElseThrow(() -> new UserNotFoundException("User with ID: " + updateUserRequestDto.userId() + " does not exist"));
    User updatedUser  = new User(updateUserRequestDto.userId().toString(),
                                 updateUserRequestDto.firstName(),
                                 updateUserRequestDto.lastName(),
                                 updateUserRequestDto.email(),
                                 existingUser.getCreatedAt(),
                                 null);
    return userRepository.save(updatedUser);
  }
}