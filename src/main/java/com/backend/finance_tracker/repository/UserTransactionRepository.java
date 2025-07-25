package com.backend.finance_tracker.repository;

import com.backend.finance_tracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "SELECT * from TRANSACTION where USER_ID = :userId",nativeQuery = true)
    List<Transaction> getTransactionsByUser(@Param("userId") long userId);

}
