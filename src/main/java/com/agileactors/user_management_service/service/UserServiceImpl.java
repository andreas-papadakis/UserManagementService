package com.agileactors.user_management_service.service;

import com.agileactors.user_management_service.dto.CreateUpdateUserRequestDTO;
import com.agileactors.user_management_service.dto.CreateUpdateUserResponseDTO;
import com.agileactors.user_management_service.dto.GetUserResponseDTO;
import com.agileactors.user_management_service.model.User;
import com.agileactors.user_management_service.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    UserRepository userRepository;

    public CreateUpdateUserResponseDTO createUser(CreateUpdateUserRequestDTO user) {
        User user1 = userRepository.save(new User(user.firstName(), user.lastName(), user.email()));
        return new CreateUpdateUserResponseDTO(user1.getId(),
                                               user1.getFirstName(),
                                               user1.getLastName(),
                                               user1.getEmail(),
                                               user1.getCreatedAt(),
                                               user1.getUpdatedAt());
    }

    public List<GetUserResponseDTO> getAllUsers(String first_name) {
        return (first_name.isBlank())
                ? userRepository.findAll()
                                .stream()
                                .map(user -> new GetUserResponseDTO(user.getFirstName(), user.getLastName(), user.getEmail()))
                                .toList()
                : userRepository.findByFirstNameLike("%" + first_name + "%")
                                .stream()
                                .map(user -> new GetUserResponseDTO(user.getFirstName(), user.getLastName(), user.getEmail()))
                                .toList();
    }

    public Optional<GetUserResponseDTO> getUserById(String user_id) {
        return userRepository.findById(user_id).map(user -> new GetUserResponseDTO(user.getFirstName(), user.getLastName(), user.getEmail()));
    }

    public int deleteUser(String user_id) {
        return userRepository.deleteUserById(user_id);
    }

    public int deleteAllUsers() {
        return userRepository.deleteAllUsers();
    }

    public CreateUpdateUserResponseDTO updateUser(String user_id, CreateUpdateUserRequestDTO updated_user) {
        Optional<User> optionalUser = userRepository.findById(user_id);

        if(optionalUser.isPresent()) {
            User user = userRepository.save(new User(user_id,
                                                     updated_user.firstName(),
                                                     updated_user.lastName(),
                                                     updated_user.email(),
                                                     optionalUser.get().getCreatedAt()));
            return new CreateUpdateUserResponseDTO(user.getId(),
                                                   user.getFirstName(),
                                                   user.getLastName(),
                                                   user.getEmail(),
                                                   user.getCreatedAt(),
                                                   user.getUpdatedAt());
        }
        return new CreateUpdateUserResponseDTO("", "", "", "", LocalDateTime.now(), LocalDateTime.now());
    }
}