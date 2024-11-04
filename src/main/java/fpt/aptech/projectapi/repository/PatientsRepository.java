package fpt.aptech.projectapi.repository;

import fpt.aptech.projectapi.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepository extends JpaRepository<Patient, Integer> {
}
