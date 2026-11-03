package com.aryan.jobportal.auth.security;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.enums.AppUserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String JWT_SECRET_KEY;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(AppUser user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*15))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(AppUser user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000L*60*60*24*30*6))
                .signWith(getSecretKey())
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        return UUID.fromString(parseClaims(token).getSubject());
    }

    /**
     * Builds a lightweight principal from access-token claims so authenticated
     * requests do not hit the database on every call.
     */
    public AppUser getUserFromAccessToken(String token) {
        Claims claims = parseClaims(token);
        return AppUser.builder()
                .id(UUID.fromString(claims.getSubject()))
                .email(claims.get("email", String.class))
                .role(AppUserRole.valueOf(claims.get("role", String.class)))
                .build();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
