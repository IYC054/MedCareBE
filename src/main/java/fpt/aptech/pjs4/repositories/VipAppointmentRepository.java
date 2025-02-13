package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.VipAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VipAppointmentRepository extends JpaRepository<VipAppointment, Integer> {

}
