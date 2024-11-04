package fpt.aptech.projectapi.repository;

import fpt.aptech.projectapi.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalsRepository extends JpaRepository<Hospital, Integer> {
}
