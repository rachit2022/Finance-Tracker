package com.backend.finance_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.finance_tracker.entity.Role;
import com.backend.finance_tracker.entity.User;
import com.backend.finance_tracker.repository.RoleRepository;
import com.backend.finance_tracker.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

//    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

//    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("User not present");
        } else {
            Role role = roleRepository.findByName(roleName);
            user.setRole(role.getName());
        }
    }

//    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> authenticateUser(String email, String password){
        return userRepository.authenticateUser(email,password);
    }

}
