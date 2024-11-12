package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.PatientFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientFileRepository extends JpaRepository<PatientFile, Integer> {
}
