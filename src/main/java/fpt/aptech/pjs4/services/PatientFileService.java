package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.PatientFile;

import java.util.List;

public interface PatientFileService {
    PatientFile createPatientFile(PatientFile patientFile);

    PatientFile getPatientFileById(int id);
    List<PatientFile> getPatientFileByDoctorId(int doctorid);

    List<PatientFile> getAllPatientFiles();

    PatientFile updatePatientFile(int id, PatientFile patientFile);

    void deletePatientFile(int id);
}
