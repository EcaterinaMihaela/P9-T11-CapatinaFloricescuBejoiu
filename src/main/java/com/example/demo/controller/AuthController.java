package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.PasswordResetService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final PasswordResetService passwordResetService;

    public AuthController(AuthService authService, UserService userService, PasswordResetService passwordResetService) {
        this.authService = authService;
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDTO dto) {
        User user = authService.register(dto);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> body) {

        String email = body.get("email");

        authService.createPasswordResetToken(email);

        return ResponseEntity.ok("Reset link sent");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String code = body.get("code");

        boolean valid = passwordResetService.verifyCode(email, code);

        if (!valid) {
            return ResponseEntity.badRequest().body("Invalid or expired code");
        }

        return ResponseEntity.ok("Code valid");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        boolean result = passwordResetService.resetPasswordByEmail(email, password);

        if (!result) {
            return ResponseEntity.badRequest().body("Reset failed");
        }

        return ResponseEntity.ok("Password changed successfully");
    }
}