package fpt.aptech.projectapi.repository;

import fpt.aptech.projectapi.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorsRepository extends JpaRepository<Doctor, Integer> {
}
