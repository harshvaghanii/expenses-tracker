package com.bu.harshvaghani.backend.controller;

import com.bu.harshvaghani.backend.dto.BalanceDTO;
import com.bu.harshvaghani.backend.services.BalanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/balances")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/{user1Id}/{user2Id}")
    public ResponseEntity<BalanceDTO> getBalanceBetweenUsers(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        BalanceDTO balanceDTO = balanceService.getBalanceBetweenUsers(user1Id, user2Id);
        return new ResponseEntity<>(balanceDTO, HttpStatus.OK);
    }
}
