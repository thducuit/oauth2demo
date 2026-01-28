package com.example.demo.config;

import com.example.demo.filter.AuthenticationFilter;
import com.example.demo.service.JwtService;
import com.example.demo.service.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
/*@EnableMethodSecurity(securedEnabled = true) => for using @Secured */
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService, SessionService sessionService) throws Exception {
        System.out.println("Hit !!");

        http
                .csrf(AbstractHttpConfigurer::disable)
//                .addFilterBefore(authenticationFilter(jwtService, sessionService), AnonymousAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter(jwtService, sessionService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/dev").permitAll()
//                        .requestMatchers(("/api/**")).hasRole("MANAGER")
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(JwtService jwtService, SessionService sessionService) {
        return new AuthenticationFilter(jwtService, sessionService);
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("""
        ROLE_MANAGER > ROLE_CSO
        ROLE_CSO > ROLE_LEADER
        ROLE_LEADER > ROLE_STAFF
    """);
    }
}
