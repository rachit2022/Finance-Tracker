package com.backend.finance_tracker.security.controller;


import com.backend.finance_tracker.entity.User;
import com.backend.finance_tracker.repository.UserRepository;
import com.backend.finance_tracker.security.entity.AuthRequest;
import com.backend.finance_tracker.security.util.JWTUtil;
import com.backend.finance_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest){

        try{
            Optional<User> user= userService.authenticateUser(authRequest.getEmail(),authRequest.getPassword());

            if(user.isEmpty()){
                return "User doesn't exist";
            }

            return jwtUtil.generateToken(authRequest.getEmail());
        }catch (Exception e){
            e.printStackTrace();
            return "Exception occurred while generating token";
        }
    }


}
