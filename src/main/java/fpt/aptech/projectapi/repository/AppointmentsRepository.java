package fpt.aptech.projectapi.repository;

import fpt.aptech.projectapi.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentsRepository extends JpaRepository<Appointment, Integer> {
}
