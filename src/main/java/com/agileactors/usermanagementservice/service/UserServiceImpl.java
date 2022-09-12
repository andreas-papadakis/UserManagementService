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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@SuppressWarnings("checkstyle:LineLengthCheck")
@Service
class UserServiceImpl implements UserService {
  @Autowired
  UserRepository userRepository;

  public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto, BindingResult errors) {
    if (errors.hasErrors()) {
      throw new InvalidArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
    }
    if (!createUserRequestDto.email().matches("[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+")) {
      throw new InvalidArgumentException("Invalid email.");
    }
    User user = userRepository.save(new User(UUID.randomUUID().toString(),
                                             createUserRequestDto.firstName(),
                                             createUserRequestDto.lastName(),
                                             createUserRequestDto.email(),
                                             null,
                                             null));
    return new CreateUserResponseDto(UUID.fromString(user.getId()),
                                                     user.getFirstName(),
                                                     user.getLastName(),
                                                     user.getEmail(),
                                                     user.getCreatedAt(),
                                                     user.getUpdatedAt());
  }

  public List<GetUserResponseDto> getAllUsers(String searchTerm) {
    return (searchTerm.isBlank())
            ? userRepository.findAll()
                            .stream()
                            .map(user -> new GetUserResponseDto(user.getFirstName(),
                                                                user.getLastName(),
                                                                user.getEmail()))
                            .toList()
            : userRepository.findByFirstNameLike("%" + searchTerm + "%")
                            .stream()
                            .map(user -> new GetUserResponseDto(user.getFirstName(),
                                                                user.getLastName(),
                                                                user.getEmail()))
                            .toList();
  }

  public GetUserResponseDto getUserById(UUID userId) {
    User retrievedUser = userRepository.findById(userId.toString())
                                       .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " does not exist"));
    return new GetUserResponseDto(retrievedUser.getFirstName(),
                                  retrievedUser.getLastName(),
                                  retrievedUser.getEmail());
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