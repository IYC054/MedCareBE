package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.PatientFile;
import fpt.aptech.pjs4.repositories.PatientFileRepository;
import fpt.aptech.pjs4.services.PatientFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientFileServiceImpl implements PatientFileService {
    @Autowired
    private PatientFileRepository patientFileRepository;

    @Override
    public PatientFile createPatientFile(PatientFile patientFile) {
        return patientFileRepository.save(patientFile);
    }

    @Override
    public PatientFile getPatientFileById(int id) {
        return patientFileRepository.findById(id).orElse(null);
    }

    @Override
    public List<PatientFile> getAllPatientFiles() {
        return patientFileRepository.findAll();
    }

    @Override
    public PatientFile updatePatientFile(int id, PatientFile patientFile) {
        if (patientFileRepository.existsById(id)) {
            patientFile.setId(id);
            return patientFileRepository.save(patientFile);
        }
        return null;
    }

    @Override
    public void deletePatientFile(int id) {
        patientFileRepository.deleteById(id);
    }
}
