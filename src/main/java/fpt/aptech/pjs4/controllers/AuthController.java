package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendOtp(@RequestParam String email) {
        String response = authService.sendOtp(email);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", response);

        if (response.contains("exceeded") || response.contains("need to wait")) {
            return ResponseEntity.badRequest().body(responseBody);
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = authService.verifyOtp(email, otp);

        Map<String, String> response = new HashMap<>();
        if (isValid) {
            response.put("message", "OTP verified successfully!");
            return ResponseEntity.ok(response); // ✅ Trả về JSON
        } else {
            response.put("error", "Invalid or expired OTP!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    //forgot password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        authService.sendOtpForgotPassword(email);
        return ResponseEntity.ok("OTP has been sent to your email if the account exists.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
                                                @RequestParam String otp,
                                                @RequestParam String newPassword) {
        authService.resetPassword(email, otp, newPassword);
        return ResponseEntity.ok("Password has been reset successfully.");
    }
}
