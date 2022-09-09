package com.agileactors.userManagementService.service;

import com.agileactors.userManagementService.dto.CreateUpdateUserRequestDto;
import com.agileactors.userManagementService.dto.CreateUpdateUserResponseDto;
import com.agileactors.userManagementService.dto.GetUserResponseDto;
import com.agileactors.userManagementService.dto.UpdateUserRequestDto;
import com.agileactors.userManagementService.model.User;
import com.agileactors.userManagementService.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public CreateUpdateUserResponseDto createUser(CreateUpdateUserRequestDto user) {
        User user1 = userRepository.save(new User(user.firstName(), user.lastName(), user.email()));
        return new CreateUpdateUserResponseDto(user1.getId(),
                                               user1.getFirstName(),
                                               user1.getLastName(),
                                               user1.getEmail(),
                                               user1.getCreatedAt(),
                                               user1.getUpdatedAt());
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
        User existingUser = userRepository.findById(updateUserRequestDto.userId()).orElseThrow(new UserNotFoundException);

        if(optionalUser.isPresent()) {
            User user = userRepository.save(new User(updateUserRequestDto.userId(),
                                                     updateUserRequestDto.firstName(),
                                                     updateUserRequestDto.lastName(),
                                                     updateUserRequestDto.email(),
                                                     optionalUser.get().getCreatedAt()));
            return user;
        }
        return new CreateUpdateUserResponseDto("", "", "", "", LocalDateTime.now(), LocalDateTime.now());
    }
}