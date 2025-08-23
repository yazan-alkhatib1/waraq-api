package com.waraq.service.helpers;

import com.waraq.dto.auth.CustomUserDetails;
import com.waraq.repository.entities.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static com.waraq.repository.enums.Role.CLIENT;

@Component
public class JwtTokenManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 7008375124389347049L;

    private static final String BEARER = "Bearer ";

    private final String jwtSecret;

    private final Long jwtExpiration;

    public JwtTokenManager(@Value("${pa.jwt.secret}") String jwtSecret,
                           @Value("${pa.jwt.expiration}") Long jwtExpiration) {
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
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
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
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
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
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
