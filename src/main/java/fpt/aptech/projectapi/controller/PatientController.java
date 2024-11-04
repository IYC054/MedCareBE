package fpt.aptech.projectapi.controller;

import fpt.aptech.projectapi.entities.Patient;
import fpt.aptech.projectapi.services.PatientsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patients")
@AllArgsConstructor
public class PatientController {
    private final PatientsService patientsService;

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientsService.createPatient(patient);
        return ResponseEntity.status(201).body(createdPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        Patient patient = patientsService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patientList = patientsService.getAllPatients();
        return ResponseEntity.ok(patientList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @RequestBody Patient patient) {
        Patient updatedPatient = patientsService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        patientsService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
