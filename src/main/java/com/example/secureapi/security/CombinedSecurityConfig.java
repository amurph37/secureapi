package com.example.secureapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;

@Configuration
public class CombinedSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/public/**").permitAll()   // Public endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Admin role only
                        .requestMatchers("/api/user/**").hasRole("USER")   // User role only
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults -> {}) // Re-enable HTTP Basic with defaults
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())) // Configure JWT handling
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))  // Handles unauthorized access
                        .accessDeniedHandler(accessDeniedHandler())                                   // Handles access denied
                );

        return http.build();
    }

    // Optional: Define a custom JwtAuthenticationConverter if you need to extract roles from JWT claims
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Configure converter if needed, for example, to extract authorities from JWT claims
        return converter;
    }

    // Define a custom AccessDeniedHandler
    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access Denied!");
        };
    }
}

