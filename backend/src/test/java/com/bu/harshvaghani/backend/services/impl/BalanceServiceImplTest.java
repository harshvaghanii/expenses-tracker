package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.BalanceDTO;
import com.bu.harshvaghani.backend.repositories.BalanceRepository;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BalanceServiceImplTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBalanceBetweenUsers_NoBalanceFound() {
        Long user1Id = 1L;
        Long user2Id = 2L;
        User user1 = new User();
        user1.setId(user1Id);
        User user2 = new User();
        user2.setId(user2Id);

        when(userRepository.findById(user1Id)).thenReturn(java.util.Optional.of(user1));
        when(userRepository.findById(user2Id)).thenReturn(java.util.Optional.of(user2));
        when(balanceRepository.findByUser1AndUser2(user1, user2)).thenReturn(null);

        BalanceDTO balanceDTO = balanceService.getBalanceBetweenUsers(user1Id, user2Id);
        Ò
        assertEquals(BigDecimal.ZERO, balanceDTO.getBalanceAmount());
        verify(balanceRepository, times(1)).findByUser1AndUser2(user1, user2);
    }
}
