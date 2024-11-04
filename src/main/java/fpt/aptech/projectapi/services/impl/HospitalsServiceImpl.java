package fpt.aptech.projectapi.services.impl;

import fpt.aptech.projectapi.entities.Hospital;
import fpt.aptech.projectapi.repository.HospitalsRepository;
import fpt.aptech.projectapi.services.HospitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalsServiceImpl implements HospitalsService {
    @Autowired
    private HospitalsRepository hospitalsRepository;



    @Override
    public Hospital createHospital(Hospital hospital) {
        return hospitalsRepository.save(hospital);
    }

    @Override
    public Hospital getHospitalById(int id) {
        return hospitalsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalsRepository.findAll();
    }

    @Override
    public Hospital updateHospital(int id, Hospital hospital) {
        if (hospitalsRepository.existsById(id)) {
            hospital.setId(id);
            return hospitalsRepository.save(hospital);
        }
        return null;
    }

    @Override
    public void deleteHospital(int id) {
        hospitalsRepository.deleteById(id);
    }
}
