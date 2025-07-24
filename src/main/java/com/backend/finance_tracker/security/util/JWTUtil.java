package com.backend.finance_tracker.security.util;


import com.backend.finance_tracker.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${secret.key}")
    private String SECRET;

    private SecretKey key;


    private final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 15;      // 15 minutes
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours


    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateAccessToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateAccessToken(String email, User user,String token){
        return email.equals(user.getEmail()) && !isAccessTokenExpired(token);
    }

    private boolean isAccessTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateRefreshToken(String email, User user,String token){
        return email.equals(user.getEmail()) && !isRefreshTokenExpired(token);
    }

    private boolean isRefreshTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

}
