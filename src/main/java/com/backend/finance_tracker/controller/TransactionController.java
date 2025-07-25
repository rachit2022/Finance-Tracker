package com.backend.finance_tracker.controller;


import com.backend.finance_tracker.dto.TransactionDto;
import com.backend.finance_tracker.entity.Transaction;
import com.backend.finance_tracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addTransaction(
            @RequestBody TransactionDto transactionDto,
            @PathVariable(value = "userId") long userId ){

        try{
            return transactionService.addNewTransaction(userId,transactionDto);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Exception occurred while adding new transaction");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTransaction(
        @RequestBody TransactionDto transactionDtp,
        @RequestParam(value = "transactionId") long transactionId
    ){
        try{
            return transactionService.updateTransaction(transactionId,transactionDtp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Exception occurred while updating your transaction");
        }
    }


    @GetMapping("/get/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable(value = "transactionId") long transactionId){

        try {
            return transactionService.getTransaction(transactionId);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTransaction(
            @RequestBody List<Long> transactionIds
    ){
        try{
            return transactionService.deleteTransaction(transactionIds);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Exception occurred while deleting your transaction");
        }
    }


    @GetMapping("/filter")
    public ResponseEntity<List<Transaction>> getFilteredTransactions(
            @RequestParam long userId,

            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) String transactionCategory,
            @RequestParam(required = false) String transactionAccount,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate
    ) {
        try {
            return transactionService.getFilteredTransactions(userId, transactionType, transactionCategory, transactionAccount, fromDate, toDate);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }


}


