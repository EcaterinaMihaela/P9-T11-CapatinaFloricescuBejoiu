package com.example.demo.service.impl;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.model.Member;
import com.example.demo.model.PasswordResetToken;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.AuthService;
import com.example.demo.service.EmailService;
import com.example.demo.service.PasswordResetService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.UserProfile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

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
            return new LoginResponseDTO(null, null, "FAIL", "INVALID_USERNAME", null, null);
        }

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponseDTO(null, null, "FAIL", "INVALID_PASSWORD", null, null);
        }

        Member member = repo.member.findByUser_Userid(user.getUserid()).orElse(null);
        Long memberId = (member != null) ? member.getMemberID() : null;

        String email = (user.getProfile() != null)
                ? user.getProfile().getEmail()
                : "No Email";

        return new LoginResponseDTO(
                user.getUserid(),
                user.getUsername(),
                user.getRole(),
                "OK",
                email,
                memberId
        );
    }

    @Transactional
    @Override
    public User register(RegisterRequestDTO dto) {

        System.out.println("=== REGISTER START ===");
        System.out.println("Username: " + dto.getUsername());

        if (repo.user.findByUsername(dto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        System.out.println("Creating User object...");
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole("MEMBER");

        System.out.println("Creating UserProfile object...");
        UserProfile profile = new UserProfile();
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setEmail(dto.getEmail());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setAddress(dto.getAddress());

        profile.setUser(user);
        user.setProfile(profile);

        System.out.println("Saving User...");
        User savedUser = repo.user.saveAndFlush(user);
        System.out.println("User saved with ID: " + savedUser.getUserid());

        System.out.println("Creating Member object...");
        Member member = new Member();
        member.setMemberID(savedUser.getUserid());  // ← Setează memberID (PK)
        member.setUser(savedUser);                  // ← Setează user (FK)
        member.setBorrowLimit(5);
        member.setStatus("ACTIVE");

        System.out.println("Saving Member...");
        Member savedMember = repo.member.saveAndFlush(member);
        System.out.println("Member saved with ID: " + savedMember.getMemberID());

        return savedUser;
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