package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Doctorworking;
import fpt.aptech.pjs4.services.DoctorWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workinghours")
public class DoctorWorkingHourController {

    @Autowired
    private DoctorWorkingHourService workingHourService;

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
    public ResponseEntity<Doctorworking> addWorkingHour(@RequestBody Doctorworking workingHour) {
        Doctorworking newWorkingHour = workingHourService.addWorkingHour(workingHour);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkingHour);
    }

    // Cập nhật giờ làm việc
    @PutMapping("/{id}")
    public ResponseEntity<Doctorworking> updateWorkingHour(@PathVariable int id, @RequestBody Doctorworking workingHour) {
        workingHour.setId(id);
        Doctorworking updatedWorkingHour = workingHourService.updateWorkingHour(workingHour);
        if (updatedWorkingHour != null) {
            return ResponseEntity.ok(updatedWorkingHour);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Xóa giờ làm việc
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkingHour(@PathVariable int id) {
        workingHourService.deleteWorkingHour(id);
        return ResponseEntity.noContent().build();
    }

    // Lấy giờ làm việc theo bác sĩ
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Doctorworking>> getWorkingHoursByDoctor(@PathVariable int doctorId) {
        List<Doctorworking> workingHours = workingHourService.getWorkingHoursByDoctor(doctorId);
        if(workingHours == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        return ResponseEntity.ok(workingHours);
    }
}
