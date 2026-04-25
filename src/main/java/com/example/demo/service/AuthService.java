package com.example.demo.service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.model.User;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);

    User register(RegisterRequestDTO dto);
    void createPasswordResetToken(String email);
    boolean resetPassword(String token, String newPassword);
}