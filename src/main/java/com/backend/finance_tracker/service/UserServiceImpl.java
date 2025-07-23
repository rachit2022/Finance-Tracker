package com.backend.finance_tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.finance_tracker.entity.Role;
import com.backend.finance_tracker.entity.User;
import com.backend.finance_tracker.repository.RoleRepository;
import com.backend.finance_tracker.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user into database");
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role into database");
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found with email: {}", email);
        } else {
            log.info("Adding new role to user into database");
            Role role = roleRepository.findByName(roleName);
            user.setRole(role.getName());
        }
    }

    @Override
    public User getUser(String email) {
        log.info("Getting user from database");
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Getting list of users from database");
        return userRepository.findAll();
    }

}
