package fpt.aptech.projectapi.services.impl;

import fpt.aptech.projectapi.entities.Specialty;
import fpt.aptech.projectapi.repository.SpecialtiesRepository;
import fpt.aptech.projectapi.services.SpecialtiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtiesServiceImpl implements SpecialtiesService {
    @Autowired
    private SpecialtiesRepository specialtiesRepository;

    @Override
    public Specialty createSpecialty(Specialty specialty) {
        return specialtiesRepository.save(specialty);
    }

    @Override
    public Specialty getSpecialtyById(int id) {
        return specialtiesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        return specialtiesRepository.findAll();
    }

    @Override
    public Specialty updateSpecialty(int id, Specialty specialty) {
        if (specialtiesRepository.existsById(id)) {
            specialty.setId(id);
            return specialtiesRepository.save(specialty);
        }
        return null;
    }

    @Override
    public void deleteSpecialty(int id) {
        specialtiesRepository.deleteById(id);
    }
}
