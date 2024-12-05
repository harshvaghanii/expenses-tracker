package com.bu.harshvaghani.backend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class SignUpDTO {
    private String name;
    private String email;
    private String password;
}
