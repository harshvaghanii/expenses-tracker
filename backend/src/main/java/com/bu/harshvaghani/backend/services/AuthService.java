package com.bu.harshvaghani.backend.services;

import com.bu.harshvaghani.backend.dto.SignUpDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;

public interface AuthService {
    String login(String email, String password);

    UserDTO signup(SignUpDTO signUpDTO);
}
