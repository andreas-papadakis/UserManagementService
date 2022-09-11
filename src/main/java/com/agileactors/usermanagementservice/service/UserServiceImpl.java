package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.CreateUserResponseDto;
import com.agileactors.usermanagementservice.dto.GetUserResponseDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.exception.InvalidArgumentException;
import com.agileactors.usermanagementservice.exception.UserNotFoundException;
import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto, BindingResult errors) {
        if(errors.hasErrors())
            throw new InvalidArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
        if(!createUserRequestDto.email().matches("[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+"))
            throw new InvalidArgumentException("Invalid email.");
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

    public List<GetUserResponseDto> getAllUsers(String first_name) {
        return (first_name.isBlank())
                ? userRepository.findAll()
                                .stream()
                                .map(user -> new GetUserResponseDto(user.getFirstName(),
                                                                    user.getLastName(),
                                                                    user.getEmail()))
                                .toList()
                : userRepository.findByFirstNameLike("%" + first_name + "%")
                                .stream()
                                .map(user -> new GetUserResponseDto(user.getFirstName(),
                                                                    user.getLastName(),
                                                                    user.getEmail()))
                                .toList();
    }

    public GetUserResponseDto getUserById(UUID user_id) {
        User retrievedUser = userRepository.findById(user_id.toString()).orElseThrow(() -> new UserNotFoundException("User with ID: " + user_id + " does not exist"));
        return new GetUserResponseDto(retrievedUser.getFirstName(),
                                      retrievedUser.getLastName(),
                                      retrievedUser.getEmail());
    }

    public int deleteUser(String user_id) {
        return userRepository.deleteUserById(user_id);
    }

    public int deleteAllUsers() {
        return userRepository.deleteAllUsers();
    }

    public User updateUser(UpdateUserRequestDto updateUserRequestDto) {
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