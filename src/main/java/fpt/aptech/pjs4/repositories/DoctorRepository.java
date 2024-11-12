package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
