package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.OTPDetails;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AccountRepository accountRepository;
    final JavaMailSender mailSender;
    public AuthServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private final ConcurrentHashMap<String, OTPDetails> otpStore = new ConcurrentHashMap<>();

    private boolean saveOtp(String email, String otp, int expiryMinutes) {
        OTPDetails otpDetails = otpStore.getOrDefault(email, new OTPDetails());
        LocalDateTime now = LocalDateTime.now();

        // Nếu là lần gửi đầu tiên hoặc quá 24 giờ kể từ lần gửi trước => Reset
        if (otpDetails.getLastRequestTime() == null ||
                Duration.between(otpDetails.getLastRequestTime(), now).toHours() >= 24) {
            otpDetails.setRequestCount(1);
        } else {
            // Kiểm tra nếu vượt quá số lần gửi trong 24 giờ
            if (otpDetails.getRequestCount() >= 5) {
                return false;
            }

            // Kiểm tra nếu gửi quá sớm (phải đợi ít nhất 3 phút giữa các lần gửi)
            if (otpDetails.getLastRequestTime().plusMinutes(1).isAfter(now)) {
                return false;
            }

            // Tăng số lần gửi OTP
            otpDetails.incrementRequestCount();
        }

        // Cập nhật thông tin OTP
        otpDetails.setOtp(otp);
        otpDetails.setExpiryTime(now.plusMinutes(expiryMinutes));
        otpDetails.setLastRequestTime(now);

        // Lưu vào HashMap
        otpStore.put(email, otpDetails);
        return true;
    }


    private boolean validateOtp(String email, String otp) {
        OTPDetails otpDetails = otpStore.get(email);

        if (otpDetails == null || otpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStore.remove(email); // Xóa OTP đã hết hạn
            return false;
        }

        boolean isValid = otpDetails.getOtp().equals(otp);

        if (isValid) {
            otpStore.remove(email); // Xóa OTP sau khi xác thực thành công
        }

        return isValid;
    }

    @Override
    public String sendOtp(String email) {
        String otp = generateOtp();

        // Lưu OTP vào bộ nhớ, kiểm tra giới hạn gửi
        boolean canSend = saveOtp(email, otp, 5); // Lưu OTP với thời hạn 5 phút
        if (!canSend) {
            return "You have exceeded the limit or need to wait before sending another OTP.";
        }

        // Gửi OTP qua email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + ". It will expire in 5 minutes.");
        mailSender.send(message);

        return "OTP has been sent to " + email;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        return validateOtp(email, otp);
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    //forgot password
    @Override
    public String sendOtpForgotPassword(String email) {
        // Kiểm tra email có tồn tại trong hệ thống
        Account account = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account with email not found"));

        // Tạo và lưu OTP
        String otp = generateOtp();
        // Lưu OTP vào bộ nhớ, kiểm tra giới hạn gửi
        boolean canSend = saveOtp(email, otp, 5); // Lưu OTP với thời hạn 5 phút
        if (!canSend) {
            return "You have exceeded the limit or need to wait before sending another OTP.";
        }

        // Gửi OTP qua email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp + ". It is valid for 5 minutes.");
        mailSender.send(message);
        return "OTP has been sent to " + email;
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        // Tìm người dùng và cập nhật mật khẩu
        Account account = accountRepository.findByEmailreset(email);

        // Hash mật khẩu trước khi lưu
        String encodedPassword = new BCryptPasswordEncoder().encode(newPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }
}
