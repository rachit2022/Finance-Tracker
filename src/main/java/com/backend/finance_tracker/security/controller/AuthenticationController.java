package com.backend.finance_tracker.security.controller;


import com.backend.finance_tracker.entity.User;
import com.backend.finance_tracker.security.dto.TokenDTO;
import com.backend.finance_tracker.security.entity.AuthRequest;
import com.backend.finance_tracker.security.util.JWTUtil;
import com.backend.finance_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/authenticate")
    public ResponseEntity<TokenDTO> generateToken(@RequestBody AuthRequest authRequest){
        TokenDTO dto=new TokenDTO();
        try{
            Optional<User> user= userService.authenticateUser(authRequest.getEmail(),authRequest.getPassword());

            if(user.isEmpty()){
                return ResponseEntity.badRequest().body(dto);
            }
            dto.setAccessToken(jwtUtil.generateAccessToken(authRequest.getEmail()));
            dto.setRefreshToken(jwtUtil.generateRefreshToken(authRequest.getEmail()));
            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(dto);
        }
    }


}
