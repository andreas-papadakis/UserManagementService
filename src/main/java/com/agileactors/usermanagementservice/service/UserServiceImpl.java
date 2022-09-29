package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.exception.UserNotFoundException;
import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(CreateUserRequestDto createUserRequestDto, BindingResult errors) {
    return userRepository.save(new User(UUID.randomUUID(),
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
    return userRepository.findById(userId)
                         .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId
                                                                    + " does not exist"));
  }

  public int deleteUser(UUID userId) {
    return userRepository.deleteUserById(userId);
  }

  public int deleteAllUsers() {
    return userRepository.deleteAllUsers();
  }

  public User updateUser(UpdateUserRequestDto updateUserRequestDto, BindingResult errors) {
    User existingUser = userRepository.findById(updateUserRequestDto.userId())
                                      .orElseThrow(() -> new UserNotFoundException("User with ID: "
                                                                    + updateUserRequestDto.userId()
                                                                    + " does not exist"));
    User updatedUser  = new User(updateUserRequestDto.userId(),
                                 updateUserRequestDto.firstName(),
                                 updateUserRequestDto.lastName(),
                                 updateUserRequestDto.email(),
                                 existingUser.getCreatedAt(),
                                 null);
    return userRepository.save(updatedUser);
  }
}