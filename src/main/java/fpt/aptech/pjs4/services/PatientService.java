package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.DTOs.PatientDTO;
import fpt.aptech.pjs4.entities.Patient;
import java.util.List;

public interface PatientService {
    Patient createPatient(int accountid);

    Patient getPatientById(int id);

    List<Patient> getAllPatients();

    Patient updatePatient(int id, Patient patient);

    void deletePatient(int id);

    List<Patient> getPatientsByAccountId(Integer accountId);
}