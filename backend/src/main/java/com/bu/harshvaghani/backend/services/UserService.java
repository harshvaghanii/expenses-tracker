package com.bu.harshvaghani.backend.services;

import com.bu.harshvaghani.backend.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);
}

