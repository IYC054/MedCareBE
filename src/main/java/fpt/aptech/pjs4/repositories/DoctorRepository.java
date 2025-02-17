package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Doctor;
import fpt.aptech.pjs4.entities.Doctorworking;
import fpt.aptech.pjs4.entities.PatientFile;
import fpt.aptech.pjs4.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query("SELECT v FROM Doctor v WHERE v.account.id = :accountId")
    Doctor findDoctorByAccountId(@Param("accountId") int accountId);
    @Query("SELECT ds.doctor  FROM DoctorsSpecialty ds WHERE ds.doctor.id = :id")
    List<Doctor> findDoctorBySpecialtiesid(@Param("id") Integer id);
}
