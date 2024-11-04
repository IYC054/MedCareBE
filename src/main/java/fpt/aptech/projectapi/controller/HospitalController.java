package fpt.aptech.projectapi.controller;

import fpt.aptech.projectapi.entities.Hospital;
import fpt.aptech.projectapi.services.HospitalsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hospital")
@AllArgsConstructor
public class HospitalController {
    private HospitalsService hospitalsService;

    @PostMapping
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {
        Hospital createdHospital = hospitalsService.createHospital(hospital);
        return ResponseEntity.status(201).body(createdHospital);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable int id) {
        Hospital hospital = hospitalsService.getHospitalById(id);
        return  ResponseEntity.ok(hospital);
    }

    @GetMapping
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalsService.getAllHospitals();
        return ResponseEntity.ok(hospitals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable int id, @RequestBody Hospital hospital) {
        Hospital updatedHospital = hospitalsService.updateHospital(id, hospital);
        return ResponseEntity.ok(updatedHospital);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable int id) {
        hospitalsService.deleteHospital(id);
        return ResponseEntity.noContent().build();
    }
}
