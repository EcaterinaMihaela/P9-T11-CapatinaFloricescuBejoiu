package com.example.demo.service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.model.User;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);

    User register(RegisterRequestDTO dto);
    boolean resetPassword(String token, String newPassword);
    void createPasswordResetToken(String email);
}