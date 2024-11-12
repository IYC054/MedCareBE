package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Hospital;
import fpt.aptech.pjs4.repositories.HospitalRepository;
import fpt.aptech.pjs4.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public Hospital createHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @Override
    public Hospital getHospitalById(int id) {
        return hospitalRepository.findById(id).orElse(null);
    }

    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital updateHospital(int id, Hospital hospital) {
        if (hospitalRepository.existsById(id)) {
            hospital.setId(id);
            return hospitalRepository.save(hospital);
        }
        return null;
    }

    @Override
    public void deleteHospital(int id) {
        hospitalRepository.deleteById(id);
    }
}
