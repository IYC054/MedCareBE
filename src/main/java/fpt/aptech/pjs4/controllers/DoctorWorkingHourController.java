package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.DoctorWorking;
import fpt.aptech.pjs4.services.DoctorWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workinghours")
public class DoctorWorkingHourController {
    @Autowired
    private DoctorWorkingHourService workingHourService;
    @GetMapping()
    public ResponseEntity<List<DoctorWorking>> getWorkingHours() {
        List<DoctorWorking> doctorWorkingHours = workingHourService.getAllWorkingHours();
        return ResponseEntity.ok(doctorWorkingHours);
    }
}
