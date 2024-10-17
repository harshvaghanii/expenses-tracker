package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.BalanceDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.entities.Balance;
import com.bu.harshvaghani.backend.entities.User;
import com.bu.harshvaghani.backend.repositories.BalanceRepository;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import com.bu.harshvaghani.backend.services.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final UserRepository userRepository;

    @Autowired
    public BalanceServiceImpl(BalanceRepository balanceRepository, UserRepository userRepository) {
        this.balanceRepository = balanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BalanceDTO getBalanceBetweenUsers(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user1Id));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user2Id));

        Balance balance = balanceRepository.findByUser1AndUser2(user1, user2);
        if (balance == null) {
            return new BalanceDTO(new UserDTO(user1.getName(), user1.getEmail(), user1.getCreatedAt(), user1.getUpdatedAt()),
                    new UserDTO(user2.getName(), user2.getEmail(), user2.getCreatedAt(), user2.getUpdatedAt()),
                    BigDecimal.ZERO, null);
        }

        return new BalanceDTO(new UserDTO(user1.getName(), user1.getEmail(), user1.getCreatedAt(), user1.getUpdatedAt()),
                new UserDTO(user2.getName(), user2.getEmail(), user2.getCreatedAt(), user2.getUpdatedAt()),
                balance.getBalanceAmount(), balance.getLastUpdated());
    }

    @Override
    public void updateBalance(UserDTO paidBy, UserDTO owedBy, BigDecimal amount) {
        // Implementation to update balance between two users based on a new transaction.
        // You would update or create a new `Balance` record here.
    }
}
