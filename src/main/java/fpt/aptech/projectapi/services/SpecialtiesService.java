package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.entities.Specialty;

import java.util.List;

public interface SpecialtiesService {
    Specialty createSpecialty(Specialty specialty);
    Specialty getSpecialtyById(int id);
    List<Specialty> getAllSpecialties();
    Specialty updateSpecialty(int id, Specialty specialty);
    void deleteSpecialty(int id);
}
