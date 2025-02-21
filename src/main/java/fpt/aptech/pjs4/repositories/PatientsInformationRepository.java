package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.PatientsInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientsInformationRepository extends JpaRepository<PatientsInformation, Integer> {
    List<PatientsInformation> findByAccountId(Integer accountId);
    @Query("SELECT p FROM PatientsInformation p WHERE p.identificationCard = :identificationCard")
    boolean findidentificationCard(String identificationCard);

}
