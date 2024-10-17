package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.entities.User;
import com.bu.harshvaghani.backend.exception.ResourceNotFoundException;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import com.bu.harshvaghani.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return new UserDTO(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        User updatedUser = userRepository.save(user);

        return new UserDTO(updatedUser.getName(), updatedUser.getEmail(), updatedUser.getCreatedAt(), updatedUser.getUpdatedAt());
    }
}
