package fpt.aptech.pjs4.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDTO {
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private Integer appointmentId;
    private String transactionDescription;
}
