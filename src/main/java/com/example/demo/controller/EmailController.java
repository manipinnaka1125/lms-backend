package com.example.demo.controller;

import com.example.demo.model.EmailRequest;
import com.example.demo.service.EmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:3000") 
public class EmailController {

    @Autowired
    private EmailManager emailManager;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        // Add a username here. It could come from the request body or be predefined.
        String username = "User";  // You could set this dynamically if available

        return emailManager.sendEmail(
            emailRequest.getToEmail(),
            username,               // Add the username here
            emailRequest.getSubject(),
            emailRequest.getMessage()
        );
    }
}
