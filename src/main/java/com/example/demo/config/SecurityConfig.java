package com.example.demo.config;

import com.example.demo.component.DevUserFilter;
import com.example.demo.filter.AuthenticationFilter;
import com.example.demo.service.JwtService;
import com.example.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private DevUserFilter devUserFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService, SessionService sessionService) throws Exception {
        System.out.println("Hit !!");

        http
                .addFilterBefore(devUserFilter, AnonymousAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable()
//                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }

//    @Bean
//    public AuthenticationFilter authenticationFilter(JwtService jwtService, SessionService sessionService) {
//        return new AuthenticationFilter(jwtService, sessionService);
//    }
}
