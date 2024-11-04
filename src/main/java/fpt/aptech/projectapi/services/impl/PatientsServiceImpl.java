package fpt.aptech.projectapi.services.impl;

import fpt.aptech.projectapi.entities.Patient;
import fpt.aptech.projectapi.repository.PatientsRepository;
import fpt.aptech.projectapi.services.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientsServiceImpl implements PatientsService {
    @Autowired
    private PatientsRepository patientsRepository;
    @Override
    public Patient createPatient(Patient patient) {
        return patientsRepository.save(patient);
    }

    @Override
    public Patient getPatientById(int id) {
        return patientsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientsRepository.findAll();
    }

    @Override
    public Patient updatePatient(int id, Patient patient) {
        if (patientsRepository.existsById(id)) {
            patient.setId(id);
            return patientsRepository.save(patient);
        }
        return null;
    }

    @Override
    public void deletePatient(int id) {
        patientsRepository.deleteById(id);
    }
}
