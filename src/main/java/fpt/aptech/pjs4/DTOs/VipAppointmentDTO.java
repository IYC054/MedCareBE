package fpt.aptech.pjs4.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter

public class VipAppointmentDTO {
    private int patientId;
    private int doctorId;
    private int profileId;
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private BigDecimal amount;
    private String type;
    private String firestoreUserId;
    private String doctorEmail;

}