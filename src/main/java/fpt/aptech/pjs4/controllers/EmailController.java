package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.EmailRequest;
import fpt.aptech.pjs4.services.impl.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendHtmlEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendHtmlEmail(
                    emailRequest.getTo(),
                    emailRequest.getSubject(),
                    emailRequest.getHtmlContent()
            );
            return "Email sent successfully!";
        } catch (MessagingException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
