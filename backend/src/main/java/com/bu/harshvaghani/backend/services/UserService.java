package com.bu.harshvaghani.backend.services;

import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    public UserEntity getUserById(Long userId);

    Optional<UserDTO> getUserByEmail(String email);

    public boolean deleteUser(Long id);

    public UserDTO updateUserById(UserDTO userDTO, Long id);

    public UserDTO updatePartialUserById(Long userId, Map<String, Object> updates);
}

