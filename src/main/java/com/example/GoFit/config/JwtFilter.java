package com.example.GoFit.config;

import com.example.GoFit.model.User;
import com.example.GoFit.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtTokenResolver jwtTokenResolver;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/swagger-ui/") || requestURI.startsWith("/v2/api-docs") ||
                requestURI.startsWith("/webjars/") || requestURI.startsWith("/swagger-resources")
                || requestURI.startsWith("/csrf") || requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/users")
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bearer token not present");
            return;
        }
        String jwtToken = authHeader.substring(7);
        String role = jwtTokenResolver.getRoleFromToken(jwtToken);
        String email = jwtTokenResolver.getEmailFromToken(jwtToken);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByEmail(email);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No user found for bearer token");
                return;
            }
        try {
            jwtTokenResolver.validateToken(jwtToken, user.getEmail(), role);
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        if (requestURI.startsWith("/users") && !"ROLE_USER".equals(role)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid role in bearer token");
        }
        filterChain.doFilter(request, response);
    }
}
