package fpt.aptech.pjs4.DTOs;

import fpt.aptech.pjs4.entities.Doctor;
import fpt.aptech.pjs4.entities.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

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
}