package com.bu.harshvaghani.backend.services;

import com.bu.harshvaghani.backend.dto.LoginDTO;
import com.bu.harshvaghani.backend.dto.SignUpDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    public UserDTO signup(SignUpDTO userDTO);

    String login(HttpServletRequest request, HttpServletResponse response, LoginDTO loginDTO);
}
