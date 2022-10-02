package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.exception.UserNotFoundException;
import com.agileactors.usermanagementservice.model.GetUserModel;
import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import com.agileactors.usermanagementservice.validations.Validator;
import java.util.List;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final Validator validator;

  UserServiceImpl(UserRepository userRepository, Validator validator) {
    this.userRepository = userRepository;
    this.validator = validator;
  }

  public User createUser(CreateUserRequestDto createUserRequestDto) {
    validator.validateEmail(createUserRequestDto.email());
    return userRepository.save(new User(UUID.randomUUID(),
                                        createUserRequestDto.firstName(),
                                        createUserRequestDto.lastName(),
                                        createUserRequestDto.email(),
                                        null,
                                        null));
  }

  public List<User> getAllUsers(GetUserModel getUserModel) {
    if (getUserModel.containsData()) {
      return userRepository.findAll().stream().toList();
    } else if (getUserModel.containsOnlyFirstName()) {
      return userRepository.findByFirstNameLike("%" + getUserModel.firstName() + "%")
                           .stream()
                           .toList();
    } else if (getUserModel.containsOnlyLastName()) {
      return userRepository.findByLastNameLike("%" + getUserModel.lastName() + "%")
                           .stream()
                           .toList();
    } else {
      return userRepository.findByFirstNameLikeAndLastNameLike("%" + getUserModel.firstName() + "%",
                                                               "%" + getUserModel.lastName() + "%");
    }
  }

  public User getUserById(UUID userId) throws UserNotFoundException {
    return userRepository.findById(userId)
                         .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId
                                                                    + " does not exist"));
  }

  public void deleteUser(UUID userId) throws EmptyResultDataAccessException {
    userRepository.deleteById(userId);
  }

  public void deleteAllUsers() {
    userRepository.deleteAllUsers();
  }

  public User updateUser(UpdateUserRequestDto updateUserRequestDto) throws UserNotFoundException {
    validator.validateEmail(updateUserRequestDto.email());
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