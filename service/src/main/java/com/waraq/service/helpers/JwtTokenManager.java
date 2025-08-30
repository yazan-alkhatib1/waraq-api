package com.waraq.service.helpers;

import com.waraq.dto.auth.CustomUserDetails;
import com.waraq.repository.entities.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static com.waraq.repository.enums.Role.CLIENT;

@Component
@Slf4j
public class JwtTokenManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 7008375124389347049L;

    private static final String BEARER = "Bearer ";

    private final SecretKey secretKey;

    private final Long jwtExpiration;

    public JwtTokenManager(@Value("${pa.jwt.secret}") String jwtSecret,
                           @Value("${pa.jwt.expiration}") Long jwtExpiration) {
        this.secretKey = initSecretKey(jwtSecret);
        this.jwtExpiration = jwtExpiration;
    }

    private SecretKey initSecretKey(String configuredSecret) {
        try {
            if (configuredSecret == null || configuredSecret.isBlank()) {
                log.warn("pa.jwt.secret is blank or not set. Generating a secure random key for HS512. DO NOT use this in production.");
                return Keys.secretKeyFor(SignatureAlgorithm.HS512);
            }
            // Try Base64 first (recommended for configuration)
            byte[] keyBytes;
            try {
                keyBytes = Base64.getDecoder().decode(configuredSecret);
            } catch (IllegalArgumentException e) {
                // Not valid Base64; use raw UTF-8 bytes
                keyBytes = configuredSecret.getBytes(StandardCharsets.UTF_8);
            }
            if (keyBytes.length < 64) { // 512 bits required for HS512
                log.warn("Configured JWT secret is too short for HS512 ({} bytes). Generating a secure random key instead. Update pa.jwt.secret to a Base64-encoded 512-bit (or longer) key.", keyBytes.length);
                return Keys.secretKeyFor(SignatureAlgorithm.HS512);
            }
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception ex) {
            log.error("Failed to initialize JWT secret key. Falling back to a generated secure key.", ex);
            return Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
    }

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsAccessFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsAccessFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        Date tokenExpirationDate = getExpirationDateFromToken(token);
        return tokenExpirationDate.before(new Date());
    }

    public String generateAccessToken(UserEntity user) {
        CustomUserDetails customUserDetails = mapUserToCustomUserDetails(user);
        return doGenerateToken(customUserDetails.getClaims(), customUserDetails.getEmail());
    }

    public String generateAccessToken(CustomUserDetails customUserDetails) {
        return doGenerateToken(customUserDetails.getClaims(), customUserDetails.getEmail());
    }

    private CustomUserDetails mapUserToCustomUserDetails(UserEntity user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .isEnabled(user.getIsEnabled())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(CLIENT.getRole())
                .build();
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return BEARER.concat(Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact());
    }

    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception exception) {
            return false;
        }
    }
}
