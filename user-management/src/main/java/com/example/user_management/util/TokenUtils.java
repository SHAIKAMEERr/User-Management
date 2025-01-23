package com.example.user_management.util;

import java.security.Key;
import java.util.Date;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenUtils {

    private static final String SECRET_KEY = "your-secret-key-your-secret-key"; // Replace with a secure key
    private static final long EXPIRATION_TIME = 86400000L; // 1 day in milliseconds

    public static String generateToken(String username) {
        try {
            log.info("Generating token for username: {}", username);
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            log.error("Error generating token: {}", e.getMessage());
            throw new RuntimeException("Error generating token", e);
        }
    }

    public static boolean validateToken(String token) {
        try {
            log.info("Validating token");
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
            return false;
        }
    }
}
