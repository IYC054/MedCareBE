package fpt.aptech.projectapi.services.impl;

import fpt.aptech.projectapi.entities.PatientFile;
import fpt.aptech.projectapi.repository.PatientFilesRepository;
import fpt.aptech.projectapi.services.PatientFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientFilesServiceImpl implements PatientFilesService {
    @Autowired
    private PatientFilesRepository patientFilesRepository;



    @Override
    public PatientFile createPatientFile(PatientFile patientFile) {
        return patientFilesRepository.save(patientFile);
    }

    @Override
    public PatientFile getPatientFileById(int id) {
        return patientFilesRepository.findById(id).orElse(null);
    }

    @Override
    public List<PatientFile> getAllPatientFiles() {
        return patientFilesRepository.findAll();
    }

    @Override
    public PatientFile updatePatientFile(int id, PatientFile patientFile) {
        if (patientFilesRepository.existsById(id)) {
            patientFile.setId(id);
            return patientFilesRepository.save(patientFile);
        }
        return null;
    }

    @Override
    public void deletePatientFile(int id) {
        patientFilesRepository.deleteById(id);
    }
}
