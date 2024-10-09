package com.bu.harshvaghani.backend.services;

import com.bu.harshvaghani.backend.dto.BalanceDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;

import java.math.BigDecimal;

public interface BalanceService {
    BalanceDTO getBalanceBetweenUsers(Long user1Id, Long user2Id);

    void updateBalance(UserDTO paidBy, UserDTO owedBy, BigDecimal amount);
}
