package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.DTOs.OTPDetails;

public interface AuthService {
    //    public boolean saveOtp(String email, String otp, int expiryMinutes);
//    public boolean validateOtp(String email, String otp);
    public String sendOtp(String email);
    public boolean verifyOtp(String email, String otp);
    //    public String generateOtp();
    public String sendOtpForgotPassword(String email);
    public void resetPassword(String email, String otp, String newPassword);

}
