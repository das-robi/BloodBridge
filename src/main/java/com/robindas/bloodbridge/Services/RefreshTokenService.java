package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.Exceptions.BadRequestException;
import com.robindas.bloodbridge.Model.RefreshToken;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final Duration ttl;
    private final SecureRandom random = new SecureRandom();

    public RefreshTokenService(RefreshTokenRepository repository,
                               @Value("${security.refresh-token.ttl:P30D}") Duration ttl) {
        this.repository = repository;
        this.ttl = ttl;
    }

    @Transactional
    public String create(Users user) {
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setTokenHash(hash(rawToken));
        token.setExpiresAt(Instant.now().plus(ttl));
        token.setRevoked(false);
        repository.save(token);
        return rawToken;
    }

    /** Validates and rotates the token, so a stolen token cannot be replayed after a refresh. */
    @Transactional
    public Users consumeAndRotate(String rawToken) {
        RefreshToken token = repository.findByTokenHashAndRevokedFalse(hash(rawToken))
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        if (!token.getExpiresAt().isAfter(Instant.now())) {
            token.setRevoked(true);
            throw new BadRequestException("Refresh token has expired");
        }

        token.setRevoked(true);
        return token.getUser();
    }

    private String hash(String value) {

        try {
            return java.util.HexFormat.of().formatHex(
                    MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is unavailable", e);
        }

    }
}
