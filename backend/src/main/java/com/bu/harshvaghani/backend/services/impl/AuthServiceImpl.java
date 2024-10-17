package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.SignUpDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.entities.User;
import com.bu.harshvaghani.backend.exception.ResourceNotFoundException;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import com.bu.harshvaghani.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Assuming you will encode passwords

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (passwordEncoder.matches(password, user.getPassword())) {
            // Generate token or session logic (if you're implementing JWT, session tokens, etc.)
            return "Login successful!";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public UserDTO signup(@org.jetbrains.annotations.NotNull SignUpDTO signUpDTO) {
        // Check if user already exists
        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new RuntimeException("User already exists with this email");
        }

        // Create new User entity
        User newUser = new User();
        newUser.setName(signUpDTO.getName());
        newUser.setEmail(signUpDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        // Save the new user
        User savedUser = userRepository.save(newUser);

        // Convert to DTO
        return new UserDTO(savedUser.getName(),
