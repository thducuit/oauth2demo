package com.example.demo.filter;

import com.example.demo.model.AuthenticatedUser;
import com.example.demo.service.JwtService;
import com.example.demo.service.SessionService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private SessionService sessionService;

    public AuthenticationFilter(JwtService jwtService, SessionService sessionService) {
        this.jwtService = jwtService;
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        // extract session
//        final String extractSessionId = jwtService.extractSessionId(token);
//
//        // validate session
//        final boolean isValidSession = sessionService.isSessionValid(extractSessionId);
//        if (!isValidSession) {
//            filterChain.doFilter(request, response);
//        }
//
//        final String username = jwtService.extractUsername(token);
//
//        AuthenticatedUser principal =
//                new AuthenticatedUser(
//                        username,
//                        extractSessionId
//                );

        // Create Authentication
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(
//                        principal,
//                        null,
//                        List.of(new SimpleGrantedAuthority("ROLE_STAFF"))
//                );

        final Authentication authentication = mockAuthForTesting(request);

        // Mark as authenticated
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private Authentication mockAuthForTesting(HttpServletRequest request) {
        String testRole = request.getHeader("X-ROLE");
        AuthenticatedUser principal =
                new AuthenticatedUser(
                        "dummy",
                        "dummySession"
                );
        if (testRole != null) {
            return new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + testRole))
            );
        }
        System.out.println("FAILED");
        return null;
    }
}
