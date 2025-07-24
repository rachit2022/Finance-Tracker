package com.backend.finance_tracker.service;

import com.backend.finance_tracker.dto.TransactionDto;
import com.backend.finance_tracker.entity.Transaction;
import com.backend.finance_tracker.entity.User;
import com.backend.finance_tracker.repository.TransactionRepository;
import com.backend.finance_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> addNewTransaction(long userId, TransactionDto transactionDto){
        Optional<User> user=userRepository.findByUserId(userId);

        if(user.isPresent()){

            if(transactionDto.getTransactionDate()==null){
                return ResponseEntity.badRequest().body("Transaction Date cannot be null");
            }

            if(transactionDto.getAmount()<=0L){
                return ResponseEntity.badRequest().body("Amount cannot have value 0");
            }

            if(transactionDto.getAccount()==null || transactionDto.getAccount().isEmpty()){
                return ResponseEntity.badRequest().body("Account cannot be null");
            }

            if(transactionDto.getCategory()==null || transactionDto.getCategory().isEmpty()){
                return  ResponseEntity.badRequest().body("Transaction needs to have any category");
            }

            if(transactionDto.getTransactionType()==null || transactionDto.getTransactionType().isEmpty()){
                return ResponseEntity.badRequest().body("Transaction type cannot be empty");
            }

            Transaction transaction = getTransaction(userId, transactionDto);
            transactionRepository.save(transaction);
            return ResponseEntity.ok("Transaction added successfully");

        }else {
            return ResponseEntity.badRequest().body("Wrong userId has been passed");
        }

    }

    private static Transaction getTransaction(long userId, TransactionDto transactionDto) {
        Transaction transaction=new Transaction();
        transaction.setTransactionDate(transactionDto.getTransactionDate());
        transaction.setAccount(transactionDto.getAccount());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setNote(transactionDto.getNote());
        transaction.setCategory(transactionDto.getCategory());
        transaction.setUserId(userId);
        transaction.setTransactionType(transactionDto.getTransactionType());
        return transaction;
    }

}
