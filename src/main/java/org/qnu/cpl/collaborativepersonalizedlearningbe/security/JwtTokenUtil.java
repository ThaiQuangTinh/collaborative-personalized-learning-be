package org.qnu.cpl.collaborativepersonalizedlearningbe.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // --- Generate token ---
    public String generateToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(User user) {
        return generateToken(Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole()
        ), user.getUserId(), accessTokenExpiration);
    }

    public String generateRefreshToken(User user) {
        return generateToken(Map.of("type", "refresh"), user.getUserId(),
                refreshTokenExpiration);
    }

    // --- Validate token ---
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
        }
        catch (ExpiredJwtException e) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }
        catch (JwtException | IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }


    // --- Extract claims ---
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public String getUsernameFromToken(String token) {
        return (String) getAllClaims(token).get("username");
    }

    public String getEmailFromToken(String token) {
        return (String) getAllClaims(token).get("email");
    }

    public String getRoleFromToken(String token) {
        return (String) getAllClaims(token).get("role");
    }

    public long getAccessTokenExpiry() {
        return accessTokenExpiration / 1000;
    }

}
