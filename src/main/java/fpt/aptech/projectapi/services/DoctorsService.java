package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.entities.Doctor;

import java.util.List;

public interface DoctorsService {
    Doctor createDoctor(Doctor doctor);
    Doctor getDoctorById(int id);
    List<Doctor> getAllDoctors();
    Doctor updateDoctor(int id, Doctor doctor);
    void deleteDoctor(int id);
}
