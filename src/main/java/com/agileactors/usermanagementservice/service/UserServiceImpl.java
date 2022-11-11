package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.exception.UserNotFoundException;
import com.agileactors.usermanagementservice.model.GetUserModel;
import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import com.agileactors.usermanagementservice.validations.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final Validator validator;
  private final ConversionService conversionService;

  UserServiceImpl(UserRepository userRepository,
                  Validator validator,
                  ConversionService conversionService) {
    this.userRepository    = userRepository;
    this.validator         = validator;
    this.conversionService = conversionService;
  }

  public User createUser(CreateUserRequestDto createUserRequestDto) {
    validator.validateEmail(createUserRequestDto.email());
    return userRepository.save(conversionService.convert(createUserRequestDto, User.class));
  }

  public List<User> getAllUsers(GetUserModel getUserModel) throws IllegalArgumentException {
    if (getUserModel.isEmpty()) {
      return userRepository.findAll();
    } else if (getUserModel.containsOnlyFirstName()) {
      return userRepository.findByFirstNameLike("%" + getUserModel.firstName() + "%");
    } else if (getUserModel.containsOnlyLastName()) {
      return userRepository.findByLastNameLike("%" + getUserModel.lastName() + "%");
    } else if (getUserModel.containsAllData()) {
      return userRepository.findByFirstNameLikeAndLastNameLike("%" + getUserModel.firstName() + "%",
                "%" + getUserModel.lastName() + "%");
    } else {
      throw new IllegalArgumentException();
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
    userRepository.deleteAll();
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