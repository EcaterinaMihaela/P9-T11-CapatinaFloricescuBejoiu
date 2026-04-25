package com.example.demo.service;

public interface EmailService {
    void sendResetCodeEmail(String to, String code);
}