package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Specialty;

import java.util.List;

public interface SpecialtyService {

    Specialty createSpecialty(Specialty specialty);

    Specialty getSpecialtyById(int id);

    List<Specialty> getAllSpecialties();

    Specialty updateSpecialty(int id, Specialty specialty);

    void deleteSpecialty(int id);
    List<Specialty> getSpecialtiesByDoctorId(Integer doctorId);
}
