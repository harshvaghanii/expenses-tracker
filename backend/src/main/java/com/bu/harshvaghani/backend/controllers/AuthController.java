package com.bu.harshvaghani.backend.controllers;

import com.bu.harshvaghani.backend.advices.ApiResponse;
import com.bu.harshvaghani.backend.dto.LoginDTO;
import com.bu.harshvaghani.backend.dto.SignUpDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/auth/")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping(path = "/some")
    public ResponseEntity<ApiResponse<String>> signup() {
        String message = "Reached here!";
        if (message == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(new ApiResponse<>(message), HttpStatus.OK);
    }

    @PostMapping(path = "signup")
    public ResponseEntity<ApiResponse<UserDTO>> signup(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = authService.signup(signUpDTO);
        return new ResponseEntity<>(new ApiResponse<>(userDTO), HttpStatus.CREATED);
    }

    @PostMapping(path = "login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request,
                                                     HttpServletResponse response) {
        String token = authService.login(request, response, loginDTO);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.OK);
    }

}

