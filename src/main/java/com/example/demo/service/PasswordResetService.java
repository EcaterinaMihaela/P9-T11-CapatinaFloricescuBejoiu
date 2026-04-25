package com.example.demo.service;

public interface PasswordResetService {

    boolean resetPassword(String token, String newPassword);
}