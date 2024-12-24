package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.PatientsInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientsInformationRepository extends JpaRepository<PatientsInformation, Integer> {
    List<PatientsInformation> findByAccountId(Integer accountId);
}
