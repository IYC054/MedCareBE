package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
