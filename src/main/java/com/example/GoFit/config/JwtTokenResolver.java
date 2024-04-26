package com.example.GoFit.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.security.Key;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@NoArgsConstructor
public class JwtTokenResolver implements Serializable {
    @Serial
    private static final long serialVersionUID = -2550185165626007488L;
    private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 100L;
    private static final String secret = "secretKey1234asdsadsafeefwefwfewqfwqe";
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes()).build()
                .parseClaimsJws(token).getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private boolean isTokenExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration)
                .before(new Timestamp(System.currentTimeMillis()));
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String role) {
        long generationTime = System.currentTimeMillis();
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        claims.put("role", role);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(subject, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Timestamp(generationTime))
                .setExpiration(new Timestamp(generationTime + JWT_TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String generateJwtToken(final String subject) {
        return doGenerateToken(new HashMap<>(), subject, "USER");
    }

    public String getEmailFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }
    public String getRoleFromToken(String token){
        return getClaimFromToken(token, claims -> claims.get("role", String.class));
    }

    public void validateToken(String token, String subject, String expectedRole) {
        try {
            if (!subject.equals(getEmailFromToken(token))) {
                throw new RuntimeException("Invalid token subject");
            }
            if (isTokenExpired(token)) {
                throw new RuntimeException("Token expired");
            }
            String role = getClaimFromToken(token, claims -> claims.get("role", String.class));
            if (role == null || !role.equals(expectedRole)) {
                throw new RuntimeException("Invalid token role");
            }
        } catch (RuntimeException e) {
            System.err.println("Validation error: " + e.getMessage());
            throw e;
        }
    }

}