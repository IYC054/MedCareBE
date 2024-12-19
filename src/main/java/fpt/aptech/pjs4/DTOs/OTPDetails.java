package fpt.aptech.pjs4.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTPDetails {
    private String otp;
    private LocalDateTime expiryTime;
    private int requestCount = 0; // Số lần yêu cầu OTP
    private LocalDateTime lastRequestTime; // Thời gian yêu cầu gần nhất

    public void incrementRequestCount() {
        this.requestCount++;
    }

}
