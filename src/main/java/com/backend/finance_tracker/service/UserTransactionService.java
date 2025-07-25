package com.backend.finance_tracker.service;


import com.backend.finance_tracker.entity.Transaction;
import com.backend.finance_tracker.repository.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTransactionService {

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    public ResponseEntity<List<Transaction>> getTransactionsByUser(long userId){
        return ResponseEntity.ok().body(userTransactionRepository.getTransactionsByUser(userId));
    }

}
