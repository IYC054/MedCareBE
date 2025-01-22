package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.PatientFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientFileRepository extends JpaRepository<PatientFile, Integer> {
    @Query("SELECT p FROM PatientFile p WHERE p.id = :id")
    PatientFile findPatientFileById(@Param("id") int id);
    @Query("SELECT p FROM PatientFile p WHERE p.doctor.id = :doctorid")
    List<PatientFile> findPatientFileByDoctorId(@Param("doctorid") int doctorId);
}
