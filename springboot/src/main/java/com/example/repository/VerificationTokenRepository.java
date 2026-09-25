package com.example.repository;

import com.example.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    
    Optional<VerificationToken> findByToken(String token);
    
    Optional<VerificationToken> findByUserIdAndTokenTypeAndUsedFalse(UUID userId, VerificationToken.TokenType tokenType);
    
    @Modifying
    @Query("DELETE FROM VerificationToken v WHERE v.expiryDate < :now")
    void deleteExpiredTokens(LocalDateTime now);
    
    @Modifying
    @Query("UPDATE VerificationToken v SET v.used = true WHERE v.id = :tokenId")
    void markAsUsed(UUID tokenId);
}