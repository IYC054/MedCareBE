package fpt.aptech.pjs4.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PatientFileDTO {
    private String prescription;
    private BigDecimal totalPrice;
    private Integer patientId; // ID của bệnh nhân
    private List<String> urlImages; // Danh sách URL của ảnh
}