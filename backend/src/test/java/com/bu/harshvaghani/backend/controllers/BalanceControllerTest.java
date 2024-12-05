package com.bu.harshvaghani.backend.controller;

import com.bu.harshvaghani.backend.dto.BalanceDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BalanceController.class)
class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService balanceService;

    @Test
    void testGetBalanceBetweenUsers() throws Exception {
        UserDTO user1 = new UserDTO("John Doe", "john@example.com", null, null);
        UserDTO user2 = new UserDTO("Jane Doe", "jane@example.com", null, null);
        BalanceDTO balanceDTO = new BalanceDTO(user1, user2, BigDecimal.valueOf(150), LocalDateTime.now());

        Mockito.when(balanceService.getBalanceBetweenUsers(1L, 2L)).thenReturn(balanceDTO);

        mockMvc.perform(get("/api/balances/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balanceAmount").value(150))
                .andExpect(jsonPath("$.user1.name").value("John Doe"))
                .andExpect(jsonPath("$.user2.name").value("Jane Doe"));
    }
}
