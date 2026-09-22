package com.robindas.bloodbridge.Repositories;

import com.robindas.bloodbridge.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.EntityGraph;
import jakarta.persistence.LockModeType;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = "user")
    Optional<RefreshToken> findByTokenHashAndRevokedFalse(String tokenHash);
    void deleteByExpiresAtBefore(Instant expiry);

}
