package com.backend.finance_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.finance_tracker.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
