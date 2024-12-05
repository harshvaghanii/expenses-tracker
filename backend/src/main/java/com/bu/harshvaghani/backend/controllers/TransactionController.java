package com.bu.harshvaghani.backend.controllers;

import com.bu.harshvaghani.backend.advices.ApiResponse;
import com.bu.harshvaghani.backend.dto.TransactionDTO;
import com.bu.harshvaghani.backend.exception.ResourceNotFoundException;
import com.bu.harshvaghani.backend.services.TransactionService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping(path = "/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionDTO>> addTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO transaction = transactionService.addTransaction(transactionDTO);
            return new ResponseEntity<>(new ApiResponse<>(transaction), HttpStatus.CREATED);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }

}
