package com.expenseApi.expenseTracker.security;

import java.sql.Date;
import java.time.Instant;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
	 private final SecretKey secretKey;
	    private final long expirationMs;
	    public JwtService(
	            @Value("${jwt.secret}") String secret,
	            @Value("${jwt.expiration-ms}") long expirationMs) {

	        byte[] keyBytes = Decoders.BASE64.decode(secret);

	        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	        this.expirationMs = expirationMs;
	    }

	    // Generate JWT token
	    public String generateToken(String email) {

	        Instant now = Instant.now();

	        return Jwts.builder()
	                .subject(email)
	                .issuedAt(Date.from(now))
	                .expiration(
	                        Date.from(now.plusMillis(expirationMs))
	                )
	                .signWith(secretKey)
	                .compact();
	    }

	    // Extract email from token
	    public String extractEmail(String token) {

	        return extractClaims(token).getSubject();
	    }

	    // Extract and validate JWT claims
	    private Claims extractClaims(String token) {

	        return Jwts.parser()
	                .verifyWith(secretKey)
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }
}
