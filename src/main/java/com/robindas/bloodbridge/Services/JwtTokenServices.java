package com.robindas.bloodbridge.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Service
public class JwtTokenServices {

    private String secreteKey = " ";


    public JwtTokenServices() {

        try {
            KeyGenerator genKey = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = genKey.generateKey();
            secreteKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String generateKey(String username) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 5))
                .and()
                .signWith(getKeys())
                .compact();
    }

    private SecretKey getKeys() {

        byte[] keyByte = Decoders.BASE64.decode(secreteKey);

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

        System.out.println("Checking Validate user: " + userDetails.getUsername());

        return username.equals(userDetails.getUsername());
    }
}
