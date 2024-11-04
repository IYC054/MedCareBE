package fpt.aptech.projectapi.services.impl;

import fpt.aptech.projectapi.entities.Account;
import fpt.aptech.projectapi.entities.Doctor;
import fpt.aptech.projectapi.repository.AccountsRepository;
import fpt.aptech.projectapi.repository.DoctorsRepository;
import fpt.aptech.projectapi.services.AccountServices;
import fpt.aptech.projectapi.services.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorsServicesImpl implements DoctorsService {
    @Autowired
    private DoctorsRepository doctorsRepository;


    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorsRepository.save(doctor);
    }

    @Override
    public Doctor getDoctorById(int id) {
        return doctorsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorsRepository.findAll();
    }

    @Override
    public Doctor updateDoctor(int id, Doctor doctor) {
        if (doctorsRepository.existsById(id)) {
            doctor.setId(id);
            return doctorsRepository.save(doctor);
        }
        return null;
    }

    @Override
    public void deleteDoctor(int id) {
        doctorsRepository.deleteById(id);
    }
}
