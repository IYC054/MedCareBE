package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.SpecialtyRequest;
import fpt.aptech.pjs4.entities.Specialty;
import fpt.aptech.pjs4.services.impl.DoctorSpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DoctorSpecialtyController {

    @Autowired
    private DoctorSpecialtyService specialtyService;
    // POST request để nhận dữ liệu về doctorId và specialtyId
    @PostMapping("/addSpecialty")
    public ResponseEntity<?> addSpecialty(@RequestBody SpecialtyRequest specialty) {
        try {
            boolean add = specialtyService.addSpecialtyToDoctor(specialty);
            if (add) {
                return ResponseEntity.ok("Add successflly");
            } else {
                // Trả về lỗi nếu không thể thêm specialty
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to add Specialty: Doctor or Specialty not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về lỗi nếu có ngoại lệ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the specialty.");
        }
    }

}
