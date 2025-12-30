package com.example.demo.config;

import com.example.demo.filter.AuthenticationFilter;
import com.example.demo.service.JwtService;
import com.example.demo.service.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService, SessionService sessionService) throws Exception {
        System.out.println("Hit !!");

        http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(authenticationFilter(jwtService, sessionService), AnonymousAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(JwtService jwtService, SessionService sessionService) {
        return new AuthenticationFilter(jwtService, sessionService);
    }
}
