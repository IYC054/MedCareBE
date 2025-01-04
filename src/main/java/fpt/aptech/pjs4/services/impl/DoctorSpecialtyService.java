package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.SpecialtyRequest;
import fpt.aptech.pjs4.entities.Doctor;
import fpt.aptech.pjs4.entities.DoctorsSpecialty;
import fpt.aptech.pjs4.entities.Specialty;
import fpt.aptech.pjs4.repositories.DoctorRepository;
import fpt.aptech.pjs4.repositories.DoctorSpecialityResopitory;
import fpt.aptech.pjs4.repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorSpecialtyService {

    @Autowired
    private DoctorSpecialityResopitory doctorsSpecialtiesRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;

    public boolean addSpecialtyToDoctor(SpecialtyRequest specialtyRequest) {
        Doctor doctor = doctorRepository.findById(specialtyRequest.getDoctorId()).get();
        if(doctor == null) {
            return false;
        }
        Specialty specialty = specialtyRepository.findSpecialtyById(specialtyRequest.getSpecialtyId());
        if(specialty == null) {
            return false;
        }
        // Kiểm tra xem specialty có tồn tại không
        DoctorsSpecialty doctorsSpecialty = new DoctorsSpecialty();
        doctorsSpecialty.setDoctor(doctor);
        doctorsSpecialty.setSpecialty(specialty);
        return true;
    }
}

