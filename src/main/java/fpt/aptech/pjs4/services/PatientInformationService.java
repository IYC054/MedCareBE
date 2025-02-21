package fpt.aptech.pjs4.services;


import fpt.aptech.pjs4.entities.PatientsInformation;

import java.util.List;

public interface PatientInformationService {
    List<PatientsInformation> getAllPatientsInformation();
    PatientsInformation getPatientsInformationById(int id);
    PatientsInformation updatePatientInformation(int id,PatientsInformation patientsInformation);
    void deletePatientInformation(int id);
    PatientsInformation addPatientInformation(PatientsInformation patientsInformation);
    List<PatientsInformation> findPatientsByAccountId(Integer accountId);
    boolean findidentificationCard(String identificationCard);
}
