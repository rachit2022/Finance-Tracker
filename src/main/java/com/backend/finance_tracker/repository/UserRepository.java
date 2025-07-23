package com.backend.finance_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.finance_tracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
