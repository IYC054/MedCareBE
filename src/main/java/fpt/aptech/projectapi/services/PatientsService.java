package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.entities.Patient;

import java.util.List;

public interface PatientsService {
    Patient createPatient(Patient patient);
    Patient getPatientById(int id);
    List<Patient> getAllPatients();
    Patient updatePatient(int id, Patient patient);
    void deletePatient(int id);
}
