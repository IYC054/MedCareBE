package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.DoctorworkingDTO;
import fpt.aptech.pjs4.entities.Doctor;
import fpt.aptech.pjs4.entities.Doctorworking;
import fpt.aptech.pjs4.repositories.DoctorWorkingHourRepository;
import fpt.aptech.pjs4.services.DoctorWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workinghours")
public class DoctorWorkingHourController {

    @Autowired
    private DoctorWorkingHourService workingHourService;
    @Autowired
    private DoctorWorkingHourRepository doctorWorkingHourRepository;

    // Lấy tất cả giờ làm việc
    @GetMapping
    public ResponseEntity<List<Doctorworking>> getAllWorkingHours() {
        List<Doctorworking> doctorWorkingHours = workingHourService.getAllWorkingHours();
        return ResponseEntity.ok(doctorWorkingHours);
    }

    // Lấy giờ làm việc theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Doctorworking> getWorkingHour(@PathVariable int id) {
        Doctorworking workingHour = workingHourService.getWorkingHour(id);
        if (workingHour != null) {
            return ResponseEntity.ok(workingHour);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Thêm giờ làm việc mới
    @PostMapping
    public ResponseEntity<List<Doctorworking>> addWorkingHour(@RequestBody DoctorworkingDTO workingHour) {
        // Get the list of Doctorworking objects from the service
        List<Doctorworking> newWorkingHours = workingHourService.addWorkingHour(workingHour);

        // Return a response with the list of created working hours and HTTP status CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkingHours);
    }


    // Cập nhật giờ làm việc
    @PutMapping("/{id}")
    public ResponseEntity<Doctorworking> updateWorkingHour(@PathVariable int id, @RequestBody DoctorworkingDTO workingHour) {
        workingHour.setId(id);
        Doctorworking updatedWorkingHour = workingHourService.updateWorkingHour(workingHour);
        if (updatedWorkingHour != null) {
            return ResponseEntity.ok(updatedWorkingHour);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Xóa giờ làm việc
    @GetMapping("/doctor/{doctorId}")
    public List<Doctorworking> getWorkingHours(@PathVariable int doctorId) {
        return workingHourService.getWorkingHoursByDoctor(doctorId);
    }

    // Xóa tất cả lịch làm việc của bác sĩ theo doctor_id
    @DeleteMapping("/doctor/{doctorId}")
    public void deleteWorkingHours(@PathVariable int doctorId) {
        workingHourService.deleteWorkingHoursByDoctor(doctorId);
    }


}
