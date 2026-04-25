package com.example.demo.service.impl;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.model.PasswordResetToken;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.AuthService;
import com.example.demo.service.EmailService;
import com.example.demo.service.PasswordResetService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.UserProfile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final RepositoryWrapper repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final PasswordResetService passwordResetService;
    private final EmailService emailService;

    public AuthServiceImpl(RepositoryWrapper repo,
                           PasswordResetService passwordResetService, EmailService emailService) {
        this.repo = repo;
        this.passwordResetService = passwordResetService;
        this.emailService = emailService;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = repo.user.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            return new LoginResponseDTO(null, null, "FAIL", "OK");
        }

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponseDTO(null, null, "FAIL", "OK");
        }

        return new LoginResponseDTO(user.getUserid(), user.getUsername(), user.getRole(), "OK");
    }

    @Override
    public User register(RegisterRequestDTO dto) {

        // verificare username unic
        if (repo.user.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 🔐 create user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole("MEMBER");

        // 👤 create profile
        UserProfile profile = new UserProfile();
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setEmail(dto.getEmail());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setAddress(dto.getAddress());

        // 🔗 link bidirectional
        profile.setUser(user);
        user.setProfile(profile);

        return repo.user.save(user);
    }
    @Override
    public boolean resetPassword(String token, String newPassword) {
        return passwordResetService.resetPassword(token, newPassword);
    }

    @Override
    public void createPasswordResetToken(String email) {

        UserProfile profile = repo.userProfile.findAll()
                .stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (profile == null) {
            throw new RuntimeException("Email not found");
        }

        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(code);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        repo.passwordResetToken.save(resetToken);


        emailService.sendResetCodeEmail(email, code);
    }
}