package fpt.aptech.pjs4.services.impl;


import fpt.aptech.pjs4.entities.PatientsInformation;
import fpt.aptech.pjs4.repositories.PatientsInformationRepository;
import fpt.aptech.pjs4.services.PatientInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientInformationServiceImp implements PatientInformationService {
    @Autowired
    private PatientsInformationRepository patientsInformationRepository;
    @Override
    public List<PatientsInformation> getAllPatientsInformation() {
        return patientsInformationRepository.findAll();
    }

    @Override
    public PatientsInformation getPatientsInformationById(int id) {
        return patientsInformationRepository.findById(id).get();
    }

    @Override
    public PatientsInformation updatePatientInformation(int id,PatientsInformation patientsInformation) {
        if (patientsInformationRepository.existsById(id)) {
            patientsInformation.setId(id);
            return patientsInformationRepository.save(patientsInformation);
        }
        return null;
    }

    @Override
    public void deletePatientInformation(int id) {
        patientsInformationRepository.deleteById(id);
    }

    @Override
    public PatientsInformation addPatientInformation(PatientsInformation patientsInformation) {
        return patientsInformationRepository.save(patientsInformation);
    }
}
