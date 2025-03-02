package fpt.aptech.pjs4.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorworkingDTO {
    private Integer id;
    private Integer doctor;
    private LocalDate workStart;
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;

}