package fpt.aptech.pjs4.DTOs;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class AppointmentDTO {
    private Integer patientId;
    private Integer doctorId;
    private String type;
    private String status;
    private BigDecimal amount;
    private Integer worktimeId;
    private Integer patientProfileId;
    private String firestoreUserId;
    private String doctorEmail;
    private Boolean bhyt;
    // Getters and setters
}