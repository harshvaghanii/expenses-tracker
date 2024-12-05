package com.bu.harshvaghani.backend.services;

import com.bu.harshvaghani.backend.entities.UserEntity;

public interface JWTService {
    public String generateToken(UserEntity userEntity);

    public Long getUserIdFromToken(String token);
}

