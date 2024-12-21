package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Doctorworking;
import fpt.aptech.pjs4.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    @Query("SELECT s FROM Specialty s WHERE s.doctor.id = :doctorId")
    List<Specialty> findSpecialtiesByDoctorId(@Param("doctorId") Integer doctorId);
}
