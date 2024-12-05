package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.SignUpDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.exception.ResourceNotFoundException;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);

        // Act
        String result = authService.login(email, password);

        // Assert
        assertEquals("Login successful!", result);
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, user.getPassword());
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> authService.login(email, password));
    }

    @Test
    void testSignup_UserAlreadyExists() {
        // Arrange
        SignUpDTO signUpDTO = new SignUpDTO("Test User", "test@example.com", "password123");

        when(userRepository.existsByEmail(signUpDTO.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.signup(signUpDTO));
    }

    @Test
    void testSignup_Success() {
        // Arrange
        SignUpDTO signUpDTO = new SignUpDTO("New User", "newuser@example.com", "password123");
        User savedUser = new User();
        savedUser.setName("New User");
        savedUser.setEmail("newuser@example.com");

        when(userRepository.existsByEmail(signUpDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signUpDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserDTO result = authService.signup(signUpDTO);

        // Assert
        assertEquals("New User", result.getName());
        assertEquals("newuser@example.com", result.getEmail());
    }
}
