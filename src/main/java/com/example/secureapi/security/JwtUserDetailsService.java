package com.example.secureapi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement logic to load user from DB or other source
        if ("user".equals(username)) {
            return org.springframework.security.core.userdetails.User
                    .withUsername("user")
                    .password("$2a$10$DOWSD7tX5iZ5PjIDgN1heOlE0pW/1kB3IDrk1.jWE9KB0ePdG3JQ.") // password encrypted as "password"
                    .roles("USER").build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
