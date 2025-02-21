package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.PatientsInformationDTO;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.PatientsInformation;
import fpt.aptech.pjs4.services.AccountService;
import fpt.aptech.pjs4.services.PatientInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@RestController
@RequestMapping("api/patientsprofile")
public class PatientInformationController {
    
    @Autowired
    private PatientInformationService patientInformationService;
    @Autowired
    private AccountService accountService;
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
    @GetMapping("/account/{id}")
    public ResponseEntity<List<PatientsInformation>> getPatientProfilebyAccountId(@PathVariable int id) {
        List<PatientsInformation> patient = patientInformationService.findPatientsByAccountId(id);
        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<PatientsInformation> addPatientInformation(@RequestBody PatientsInformationDTO patientsInformationDTO) {
        Account account = accountService.getAccountById(patientsInformationDTO.getAccountid());
        String date = patientsInformationDTO.getBirthdate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate birthDate = LocalDate.parse(date, formatter);
        PatientsInformation patientsInformation = new PatientsInformation();
        patientsInformation.setAccount(account);
        patientsInformation.setBirthdate(birthDate);
        patientsInformation.setGender(patientsInformationDTO.getGender());
        patientsInformation.setAddress(patientsInformationDTO.getAddress());
        patientsInformation.setPhone(patientsInformationDTO.getPhone());
        patientsInformation.setFullname(patientsInformationDTO.getFullname());
        patientsInformation.setIdentificationCard(patientsInformationDTO.getIdentification_card());
        patientsInformation.setNation(patientsInformationDTO.getNation());
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
    @GetMapping("/card")
    public ResponseEntity<?> deletePatientInformation(@RequestParam String identification_card) {
        boolean check = patientInformationService.findidentificationCard(identification_card);
        return ResponseEntity.ok(check);
    }
}
