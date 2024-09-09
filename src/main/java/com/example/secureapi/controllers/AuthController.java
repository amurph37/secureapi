package com.example.secureapi.controllers;

import com.example.secureapi.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        if (authentication.isAuthenticated()) {
            return jwtTokenUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid login credentials");
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        // Handle user registration logic
        return "User registered successfully!";
    }
}
