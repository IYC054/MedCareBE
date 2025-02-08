package fpt.aptech.pjs4.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PatientFileDTO {
    private String description;
    private Integer patientsInformationId; // ID của bệnh nhân
    private List<String> urlImages; // Danh sách URL của ảnh
    private Integer doctorId;
}