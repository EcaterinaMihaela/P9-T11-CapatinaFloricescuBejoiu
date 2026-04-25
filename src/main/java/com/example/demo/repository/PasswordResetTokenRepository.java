package com.example.demo.repository;

import com.example.demo.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository
        extends BaseRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);
}