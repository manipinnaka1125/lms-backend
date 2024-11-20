package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailManager {

    @Autowired
    private JavaMailSender mailSender;

    public String sendEmail(String toEmail, String username, String subject, String userMessage) {
        if (toEmail == null || toEmail.trim().isEmpty()) {
            return "Error: The recipient email address is null or empty.";
        }

        try {
            // Construct the personalized email content
            String messageText = String.format(
                "📚 Hi %s,\n\n" +
                "Thank you for reaching out to us! 😊\n\n" +
                "We’ve received your message:\n" +
                "💬 \"%s\"\n\n" +
                "Our team will review it and get back to you as soon as possible. Meanwhile, if you have any further questions or need immediate assistance, feel free to contact our admin:\n\n" +
                "🧑‍💼 Admin Name: Mani Swaroop Pinnaka\n" +
                "📞 Phone: 8465860846\n" +
                "📧 Email: maniswarooppinnaka@gmail.com\n\n" +
                "🌟 Thank you for choosing [Your LMS Name]. We’re here to help you succeed in your learning journey!\n\n" +
                "Best regards,\n" +
                "The LMS Support Team 🌟"
                , username, userMessage);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("effectivelearningsystemsteam@gmail.com"); // Sender's admin email
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(messageText);

            mailSender.send(message);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for better debugging
            return "Failed to send email: " + e.getMessage();
        }
    }

}
