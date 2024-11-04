package fpt.aptech.projectapi.repository;

import fpt.aptech.projectapi.entities.PatientFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientFilesRepository extends JpaRepository<PatientFile, Integer> {
}
