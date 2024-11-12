package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
}
