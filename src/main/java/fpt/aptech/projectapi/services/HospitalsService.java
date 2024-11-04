package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.entities.Hospital;

import java.util.List;

public interface HospitalsService {
    Hospital createHospital(Hospital hospital);
    Hospital getHospitalById(int id);
    List<Hospital> getAllHospitals();
    Hospital updateHospital(int id, Hospital hospital);
    void deleteHospital(int id);
}
