package com.backend.finance_tracker.controller;


import com.backend.finance_tracker.dto.TransactionDto;
import com.backend.finance_tracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<String> addTransactions(
            @RequestBody TransactionDto transactionDto,
            @RequestParam(name = "userId") long userId ){

        try{
            return transactionService.addNewTransaction(userId,transactionDto);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Exception occurred while adding new transaction");
        }

    }

}
