package fpt.aptech.projectapi.repository;

import fpt.aptech.projectapi.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtiesRepository extends JpaRepository<Specialty, Integer> {
}
