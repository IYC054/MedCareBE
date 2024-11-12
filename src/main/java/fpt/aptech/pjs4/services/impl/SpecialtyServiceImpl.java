package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Specialty;
import fpt.aptech.pjs4.repositories.SpecialtyRepository;
import fpt.aptech.pjs4.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public Specialty createSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public Specialty getSpecialtyById(int id) {
        return specialtyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    @Override
    public Specialty updateSpecialty(int id, Specialty specialty) {
        if (specialtyRepository.existsById(id)) {
            specialty.setId(id);
            return specialtyRepository.save(specialty);
        }
        return null;
    }

    @Override
    public void deleteSpecialty(int id) {
        specialtyRepository.deleteById(id);
    }
}
