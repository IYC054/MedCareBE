package fpt.aptech.pjs4.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private BigDecimal amount;
    private String orderInfo;


}
