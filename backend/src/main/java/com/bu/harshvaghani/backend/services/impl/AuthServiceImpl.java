package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.LoginDTO;
import com.bu.harshvaghani.backend.dto.SignUpDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.entities.UserEntity;
import com.bu.harshvaghani.backend.exception.DuplicateResourceException;
import com.bu.harshvaghani.backend.exception.ResourceNotFoundException;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import com.bu.harshvaghani.backend.services.AuthService;
import com.bu.harshvaghani.backend.services.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public UserDTO signup(SignUpDTO signUpDTO) {
        if (doesExist(signUpDTO.getEmail())) {
            throw new DuplicateResourceException("User with email " + signUpDTO.getEmail() + " already exists!");
        }
        UserEntity toBeCreatedUser = modelMapper.map(signUpDTO, UserEntity.class);
        String plainPassword = toBeCreatedUser.getPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        toBeCreatedUser.setPassword(hashedPassword);
        UserEntity savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public String login(HttpServletRequest request, HttpServletResponse response, LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            UserEntity user = (UserEntity) authentication.getPrincipal();
            return jwtService.generateToken(user);
        } catch (AuthenticationException exception) {
            System.out.println("Reached Here!");
            throw new ResourceNotFoundException("Please check the credentials and try again!");
        }
    }

    public boolean doesExist(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

}
