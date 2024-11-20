package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.OtpData;
import com.example.demo.service.OTPService;

@RestController
@RequestMapping("/auth")
public class OTPcontroller {

    private Map<String, OtpData> otpStore = new HashMap<>();
    private static final int OTP_EXPIRY_TIME = 5 * 60 * 1000; // OTP expiry time in milliseconds (5 minutes)

    @Autowired
    private OTPService otpService; // Inject OTPService to handle email sending

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required.");
        }

        String otp = generateOtp();
        long timestamp = System.currentTimeMillis();
        otpStore.put(email, new OtpData(otp, timestamp));

        // Send OTP via email using OTPService
        String emailResponse = otpService.sendOtpEmail(email, otp);

        if (emailResponse.equals("OTP sent successfully!")) {
            return ResponseEntity.ok("OTP sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP.");
        }
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        OtpData otpData = otpStore.get(email);

        if (otpData == null || otp == null || otp.isEmpty() || !otp.equals(otpData.getOtp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }

        // Check if OTP has expired
        long otpTimestamp = otpData.getTimestamp();
        if (System.currentTimeMillis() - otpTimestamp > OTP_EXPIRY_TIME) {
            otpStore.remove(email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP has expired.");
        }

        otpStore.remove(email); // OTP is verified, so remove it from store
        return ResponseEntity.ok("OTP verified successfully.");
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }
}
