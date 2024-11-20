package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPService {
    @Autowired
    private JavaMailSender mailSender;

    // Method to send OTP email
    public String sendOtpEmail(String toEmail, String otp) {
        try {
            String messageText = "Your OTP for login is: " + otp;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("effectivelearningsystemsteam@gmail.com"); // Use your email
            message.setTo(toEmail);
            message.setSubject("Login OTP Verification");
            message.setText(messageText);

            mailSender.send(message);
            return "OTP sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();  // Log the full stack trace
            return "Failed to send OTP: " + e.getMessage(); // Include error details
        }
    }

}
