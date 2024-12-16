package fpt.aptech.pjs4.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentDataDTO { 
    private Integer id;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private LocalDate transactionDate; // Dạng ngày
    private String transactionDescription;
    private Integer appointmentId; // Chỉ trả về ID của appointment
    private String patientName; // Tên bệnh nhân từ appointment
    private String doctorName; // Tên bác sĩ từ appointment
}
