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
    private String paymentDate;
    private String status;
    private String transactionCode;
    private Integer appointmentId;
    private Instant transactionDate;
    private String transactionDescription;
}
