package com.backend.finance_tracker.service;

import java.util.List;

import com.backend.finance_tracker.entity.Role;
import com.backend.finance_tracker.entity.User;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String email, String roleName);

    User getUser(String email);

    List<User> getUsers();
}
