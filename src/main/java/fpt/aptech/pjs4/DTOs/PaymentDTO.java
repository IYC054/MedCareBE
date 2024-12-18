package fpt.aptech.pjs4.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class PaymentDTO {
    private BigDecimal amount;
    private String paymentMethod;
    private String payer;
    private String phone;
    private String status;
    private Integer appointmentId;
    private Instant transactionDate;
    private String transactionDescription;
}
