package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.PatientsInformation;
import fpt.aptech.pjs4.services.PatientInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patientsprofile")
public class PatientInformationController {
    
    @Autowired
    private PatientInformationService patientInformationService;

    @GetMapping
    public ResponseEntity<List<PatientsInformation>> getAllPatientsInformation() {
        List<PatientsInformation> patients = patientInformationService.getAllPatientsInformation();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientsInformation> getPatientsInformationById(@PathVariable int id) {
        PatientsInformation patient = patientInformationService.getPatientsInformationById(id);
        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<PatientsInformation> addPatientInformation(@RequestBody PatientsInformation patientsInformation) {
        PatientsInformation createdPatient = patientInformationService.addPatientInformation(patientsInformation);
        return ResponseEntity.status(201).body(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientsInformation> updatePatientInformation(
            @PathVariable int id, 
            @RequestBody PatientsInformation patientsInformation) {
        PatientsInformation updatedPatient = patientInformationService.updatePatientInformation(id, patientsInformation);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientInformation(@PathVariable int id) {
        patientInformationService.deletePatientInformation(id);
        return ResponseEntity.noContent().build();
    }
}
