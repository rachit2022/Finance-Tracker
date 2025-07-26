package com.backend.finance_tracker.service;

import com.backend.finance_tracker.dto.TransactionDto;
import com.backend.finance_tracker.entity.Transaction;
import com.backend.finance_tracker.entity.User;
import com.backend.finance_tracker.repository.TransactionRepository;
import com.backend.finance_tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
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

            Transaction transaction = getTransactions(userId, transactionDto);
            transactionRepository.save(transaction);
            return ResponseEntity.ok("Transaction added successfully");

        }else {
            return ResponseEntity.badRequest().body("Wrong userId has been passed");
        }

    }

    private static Transaction getTransactions(long userId, TransactionDto transactionDto) {
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

    @Transactional
    public ResponseEntity<String> updateTransaction(long transactionId,TransactionDto transactiondto){

            Transaction existingTransaction = transactionRepository.getTransaction(transactionId);
            if(existingTransaction!=null){
                existingTransaction.setTransactionType(transactiondto.getTransactionType());
                existingTransaction.setTransactionDate(transactiondto.getTransactionDate());
                existingTransaction.setNote(transactiondto.getNote());
                existingTransaction.setAmount(transactiondto.getAmount());
                existingTransaction.setCategory(transactiondto.getCategory());
                existingTransaction.setAccount(transactiondto.getAccount());
                transactionRepository.save(existingTransaction);
                return ResponseEntity.ok().body("Transaction Updated Successfully");
            }else{
                return ResponseEntity.badRequest().body("Transaction not present with the provided transactionId");
            }
    }

    public ResponseEntity<Transaction> getTransaction(long transactionId){
        Transaction transaction=transactionRepository.getTransaction(transactionId);
        return ResponseEntity.ok().body(transaction);

    }

    @Transactional
    public ResponseEntity<String> deleteTransaction(List<Long> transactionIds){
        if(transactionIds==null || transactionIds.isEmpty()){
            return ResponseEntity.badRequest().body("No transactionId's were provided.");
        }
        int batchSize = 1000;
        int total = transactionIds.size();

        for (int i = 0; i < total; i += batchSize) {
            List<Long> batch = transactionIds.subList(i, Math.min(i + batchSize, total));
            transactionRepository.deleteTransaction(batch);
        }
        return ResponseEntity.ok().body("Your transaction has been deleted successfully");
    }

    public ResponseEntity<List<Transaction>> getFilteredTransactions(
            long userId,
            String transactionType,
            String transactionCategory,
            String transactionAccount,
            Date fromDate,
            Date toDate
    ) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        if (transactionType != null && !transactionType.isEmpty()) {
            transactions = transactions.stream()
                    .filter(t -> t.getTransactionType().equalsIgnoreCase(transactionType))
                    .collect(Collectors.toList());
        }

        if (transactionCategory != null && !transactionCategory.isEmpty()) {
            transactions = transactions.stream()
                    .filter(t -> t.getCategory().equalsIgnoreCase(transactionCategory))
                    .collect(Collectors.toList());
        }

        if (transactionAccount != null && !transactionAccount.isEmpty()) {
            transactions = transactions.stream()
                    .filter(t -> t.getAccount().equalsIgnoreCase(transactionAccount))
                    .collect(Collectors.toList());
        }

        if (fromDate != null || toDate != null) {
            Date finalFrom = fromDate != null ? fromDate : new Date(0);
            Date finalTo = toDate != null ? toDate : new Date();

            transactions = transactions.stream()
                    .filter(t -> !t.getTransactionDate().before(finalFrom) && !t.getTransactionDate().after(finalTo))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok().body(transactions);
    }


}
