package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
