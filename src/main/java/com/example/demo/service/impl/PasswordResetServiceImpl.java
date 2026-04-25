package com.example.demo.service.impl;

import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.PasswordResetService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final RepositoryWrapper repo;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetServiceImpl(RepositoryWrapper repo,
                                    PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {

        var tokenEntityOpt = repo.passwordResetToken.findByToken(token);

        if (tokenEntityOpt.isEmpty()) {
            return false;
        }

        var tokenEntity = tokenEntityOpt.get();

        //  token expirat
        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        //  găsim user-ul prin email
        var profileOpt = repo.userProfile.findAll()
                .stream()
                .filter(p -> p.getEmail().equals(tokenEntity.getEmail()))
                .findFirst();

        if (profileOpt.isEmpty()) {
            return false;
        }

        var user = profileOpt.get().getUser();

        // schimbăm parola
        user.setPassword(passwordEncoder.encode(newPassword));

        repo.user.save(user);

        repo.passwordResetToken.deleteById(tokenEntity.getId());

        return true;
    }
    @Override
    public boolean verifyCode(String email, String code) {

        return repo.passwordResetToken.findAll()
                .stream()
                .anyMatch(t ->
                        t.getEmail().equals(email) &&
                                t.getToken().equals(code) &&
                                t.getExpiryDate().isAfter(LocalDateTime.now())
                );
    }
    @Override
    public boolean resetPasswordByEmail(String email, String newPassword) {

        var profileOpt = repo.userProfile.findAll()
                .stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();

        if (profileOpt.isEmpty()) {
            System.out.println("Profile not found for email: " + email);
            return false;
        }

        var user = profileOpt.get().getUser();

        if (user == null) {
            System.out.println("User is null for email: " + email);
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        repo.user.save(user);


        repo.passwordResetToken.deleteAll(
                repo.passwordResetToken.findAll()
                        .stream()
                        .filter(t -> t.getEmail().equals(email))
                        .toList()
        );

        return true;
    }
}