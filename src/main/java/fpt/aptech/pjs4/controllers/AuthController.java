package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.request.ResetPasswordRequest;
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
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = authService.verifyOtp(email, otp);

        Map<String, Object> response = new HashMap<>();
        if (isValid) {
            response.put("success", true);
            response.put("message", "OTP verified successfully!");
            return ResponseEntity.ok(response); // ✅ Return JSON with success flag
        } else {
            response.put("success", false);
            response.put("error", "Invalid or expired OTP!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam String email) {
        authService.sendOtpForgotPassword(email);

        // Create a JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "OTP has been sent to your email if the account exists.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getNewPassword());

        // Create a JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Password has been reset successfully.");

        return ResponseEntity.ok(response);
    }

}
