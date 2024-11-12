package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Hospital;
import java.util.List;


public interface HospitalService {
    Hospital createHospital(Hospital hospital);

    Hospital getHospitalById(int id);

    List<Hospital> getAllHospitals();

    Hospital updateHospital(int id, Hospital hospital);

    void deleteHospital(int id);
}
