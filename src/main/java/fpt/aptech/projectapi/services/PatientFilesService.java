package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.entities.PatientFile;

import java.util.List;

public interface PatientFilesService {
    PatientFile createPatientFile(PatientFile patientFile);
    PatientFile getPatientFileById(int id);
    List<PatientFile> getAllPatientFiles();
    PatientFile updatePatientFile(int id, PatientFile patientFile);
    void deletePatientFile(int id);
}
