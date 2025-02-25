package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Feedback;
import fpt.aptech.pjs4.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findByAccount_Id(Integer accountId);

}
