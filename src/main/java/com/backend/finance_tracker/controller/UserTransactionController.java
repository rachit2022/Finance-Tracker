package com.backend.finance_tracker.controller;


import com.backend.finance_tracker.entity.Transaction;
import com.backend.finance_tracker.service.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserTransactionController {

    @Autowired
    private UserTransactionService userTransactionService;

    @GetMapping("/getUserTransactions/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable(value = "userId") long userId){

        try{
            return userTransactionService.getTransactionsByUser(userId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
