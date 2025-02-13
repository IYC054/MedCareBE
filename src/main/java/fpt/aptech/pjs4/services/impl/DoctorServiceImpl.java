package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Doctor;
import fpt.aptech.pjs4.repositories.DoctorRepository;
import fpt.aptech.pjs4.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctorById(int id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public Doctor findDoctorByAccountId(int id) {
        return doctorRepository.findDoctorByAccountId(id);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }



    @Override
    public Doctor updateDoctor(int id, Doctor doctor) {
        if (doctorRepository.existsById(id)) {
            doctor.setId(id);
            return doctorRepository.save(doctor);
        }
        return null;
    }

    @Override
    public void deleteDoctor(int id) {
        doctorRepository.deleteById(id);
    }
}
