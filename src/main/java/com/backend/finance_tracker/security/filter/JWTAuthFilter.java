package com.backend.finance_tracker.security.filter;


import com.backend.finance_tracker.entity.User;
import com.backend.finance_tracker.security.util.JWTUtil;
import com.backend.finance_tracker.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token="";
        String email="";
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token=authHeader.substring(7);
            email= jwtUtil.extractEmail(token);
        }

        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user = userService.getUser(email);
            if(user!=null){
                if(jwtUtil.validateAccessToken(email,user,token) && jwtUtil.validateRefreshToken(email,user,token)){
                    UsernamePasswordAuthenticationToken  authenticationToken=new UsernamePasswordAuthenticationToken(user,null,null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);

    }


}
