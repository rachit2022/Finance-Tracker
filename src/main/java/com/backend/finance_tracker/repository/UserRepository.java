package com.backend.finance_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.finance_tracker.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * from USER where email = :email and password = :password",nativeQuery = true)
    Optional<User> authenticateUser(@Param(value = "email") String email,@Param(value = "password") String password);

}
