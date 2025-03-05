package fpt.aptech.pjs4.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class AppointmentDetailDTO {
    private String patientProfileName;
    private String doctorName;
    private String transactionCode;
    private String status;
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String AppointmentStatus;
    private Boolean BHYT;

    public AppointmentDetailDTO(String patientProfileName, String doctorName, String transactionCode, LocalDate workDate, LocalTime startTime, LocalTime endTime, String status, String AppointmentStatus, Boolean BHYT) {
        this.patientProfileName = patientProfileName;
        this.doctorName = doctorName;
        this.transactionCode = transactionCode;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.AppointmentStatus=AppointmentStatus;
        this.BHYT=BHYT;
    }
}
