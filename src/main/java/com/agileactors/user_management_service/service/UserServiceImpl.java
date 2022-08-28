package com.agileactors.user_management_service.service;

import com.agileactors.user_management_service.dto.CreateUserRequestDTO;
import com.agileactors.user_management_service.dto.CreateUserResponseDTO;
import com.agileactors.user_management_service.dto.GetUserResponseDTO;
import com.agileactors.user_management_service.model.User;
import com.agileactors.user_management_service.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    UserRepository userRepository;

    public CreateUserResponseDTO createUser(CreateUserRequestDTO user) {
        User user1 = userRepository.save(new User(user.firstName(), user.lastName(), user.email()));
        return new CreateUserResponseDTO(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getEmail(), user1.getCreatedAt(), user1.getUpdatedAt());
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

    public Optional<User> updateUser(String user_id, User updated_user) {
        Optional<User> optionalUser = userRepository.findById(user_id);

        return optionalUser.map(user -> userRepository.save(new User(user.getId(),
                                                                     updated_user.getFirstName(),
                                                                     updated_user.getLastName(),
                                                                     updated_user.getEmail(),
                                                                     user.getCreatedAt())));
    }
}