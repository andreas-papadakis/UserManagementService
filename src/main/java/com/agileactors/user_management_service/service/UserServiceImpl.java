package com.agileactors.user_management_service.service;

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

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers(String first_name) {
        return (first_name.isBlank()) ? userRepository.findAll() : userRepository.findByFirstNameLike("%" + first_name + "%");
    }

    public Optional<User> getUserById(String user_id) {
        return userRepository.findById(user_id);
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