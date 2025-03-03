package fpt.aptech.pjs4.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest2 {
    private BigDecimal amount;
    private String orderInfo;
    private int doctorId;
    private int workId;
    private int profileId;
    private int specialtyId;
    private String doctorEmail;

}
