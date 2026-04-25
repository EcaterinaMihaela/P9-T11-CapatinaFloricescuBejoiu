package com.example.demo.service;

public interface PasswordResetService {

    boolean resetPassword(String token, String newPassword);
    boolean verifyCode(String email, String code);
    boolean resetPasswordByEmail(String email, String newPassword);
}