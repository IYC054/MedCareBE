package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAppointmentByPatientId(int patientId);
}
