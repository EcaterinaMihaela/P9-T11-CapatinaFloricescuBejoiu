package com.example.demo.service.impl;

import com.example.demo.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

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

    @Override
    public void sendResetPasswordEmail(String to, String token) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setTo(to);
            helper.setSubject("Password Reset Request");

            String link = "http://localhost:8080/reset-password?token=" + token;

            String body = "Hi!\n\n"
                    + "You requested a password reset.\n"
                    + "Click the link below to reset your password:\n\n"
                    + link + "\n\n"
                    + "If you did not request this, please ignore this email.";

            helper.setText(body, false);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}