package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor createDoctor(Doctor doctor);

    Doctor getDoctorById(int id);

    List<Doctor> getAllDoctors();

    Doctor updateDoctor(int id, Doctor doctor);

    void deleteDoctor(int id);
}
