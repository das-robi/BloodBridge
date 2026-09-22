package com.robindas.bloodbridge.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

@Service
public class JwtTokenServices {

    private final String secretKey;
    private final long accessTokenTtlMillis;

    public JwtTokenServices(@Value("${security.jwt.secret}") String secretKey,
                            @Value("${security.jwt.access-token-ttl:PT5M}") java.time.Duration accessTokenTtl) {
        this.secretKey = secretKey;
        this.accessTokenTtlMillis = accessTokenTtl.toMillis();
    }

    public String generateKey(String username) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenTtlMillis))
                .and()
                .signWith(getKeys())
                .compact();
    }

    private SecretKey getKeys() {

        byte[] keyByte = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyByte);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {

        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKeys())
                .build().parseSignedClaims(token).getPayload();

    }

    public boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public long getAccessTokenTtlSeconds() {
        return accessTokenTtlMillis / 1000;
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
