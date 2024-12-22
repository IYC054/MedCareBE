package fpt.aptech.pjs4.DTOs;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class AppointmentDTO {
    private Integer patientId;
    private Integer doctorId;
    private String type;
    private String status;
    private BigDecimal amount;
    private Integer worktimeId;

    // Getters and setters
}