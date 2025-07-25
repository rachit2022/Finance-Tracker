package com.backend.finance_tracker.repository;

import com.backend.finance_tracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "SELECT * FROM TRANSACTION WHERE TRANSACTION_ID = :transactionId",nativeQuery = true)
    Transaction getTransaction(@Param(value = "transactionId") long transactionId);


    @Modifying
    @Query(value = "DELETE from TRANSACTION WHERE transaction_id IN (:transactionIds)",nativeQuery = true)
    void deleteTransaction(@Param(value = "transactionIds") List<Long> transactionIds);

}
