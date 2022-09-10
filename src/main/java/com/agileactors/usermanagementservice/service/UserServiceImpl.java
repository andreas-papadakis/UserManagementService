package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.CreateUserResponseDto;
import com.agileactors.usermanagementservice.dto.GetUserResponseDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        User user = userRepository.save(new User(UUID.randomUUID(),
                                                 createUserRequestDto.firstName(),
                                                 createUserRequestDto.lastName(),
                                                 createUserRequestDto.email(),
                                                 null,
                                                 null));
        return new CreateUserResponseDto(user.getId(),
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
                                .map(user -> new GetUserResponseDto(user.getFirstName(), user.getLastName(), user.getEmail()))
                                .toList()
                : userRepository.findByFirstNameLike("%" + first_name + "%")
                                .stream()
                                .map(user -> new GetUserResponseDto(user.getFirstName(), user.getLastName(), user.getEmail()))
                                .toList();
    }

    public Optional<GetUserResponseDto> getUserById(String user_id) {
        return userRepository.findById(user_id).map(user -> new GetUserResponseDto(user.getFirstName(), user.getLastName(), user.getEmail()));
    }

    public int deleteUser(String user_id) {
        return userRepository.deleteUserById(user_id);
    }

    public int deleteAllUsers() {
        return userRepository.deleteAllUsers();
    }

    public User updateUser(UpdateUserRequestDto updateUserRequestDto) {
        try {
            User existingUser = userRepository.findById(updateUserRequestDto.userId())
                                              .orElseThrow(UserNotFoundException::new);
            User updatedUser  = new User(updateUserRequestDto.userId(),
                                         updateUserRequestDto.firstName(),
                                         updateUserRequestDto.lastName(),
                                         updateUserRequestDto.email(),
                                         existingUser.getCreatedAt(),
                                         null);
            return userRepository.save(updatedUser);
        }
        catch (Exception e) {
            //TODO: handle exception
            return null;
        }
    }
}