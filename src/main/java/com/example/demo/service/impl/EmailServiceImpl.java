package com.example.demo.service.impl;

import com.example.demo.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendResetCodeEmail(String to, String code) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Code");

        message.setText(
                "Your password reset code is:\n\n" +
                        code +
                        "\n\nThis code is valid for 15 minutes."
        );

        mailSender.send(message);
    }
}