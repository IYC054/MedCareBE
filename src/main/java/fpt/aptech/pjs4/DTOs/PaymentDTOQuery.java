package fpt.aptech.pjs4.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentDTOQuery { 
    private String transactionCode;
    private String status;
    private String appointmentType;
    private String vipAppointmentType;
}
