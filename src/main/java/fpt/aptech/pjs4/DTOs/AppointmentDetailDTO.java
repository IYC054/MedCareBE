package fpt.aptech.pjs4.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class AppointmentDetailDTO {
    private String patientProfileName;
    private String doctorName;
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public AppointmentDetailDTO(String patientProfileName, String doctorName, LocalDate workDate, LocalTime startTime, LocalTime endTime) {
        this.patientProfileName = patientProfileName;
        this.doctorName = doctorName;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters và Setters
}
