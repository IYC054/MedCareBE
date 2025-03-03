package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Patient;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.repositories.PatientRepository;
import fpt.aptech.pjs4.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Patient createPatient(int accountid) {
        Account acc = accountRepository.findById(accountid).get();
        Patient patient1 = new Patient();
        patient1.setAccount(acc);
        return patientRepository.save(patient1);
    }

    @Override
    public Patient getPatientById(int id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }


    @Override
    public Patient updatePatient(int id, Patient patient) {
        if (patientRepository.existsById(id)) {
            patient.setId(id);
            return patientRepository.save(patient);
        }
        return null;
    }

    @Override
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<Patient> getPatientsByAccountId(Integer accountId) {
        return patientRepository.findByAccount_Id(accountId);

    }
}
