package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAppointmentByPatientId(int patientId);
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Appointment> findAppointmentByDoctorId(int doctorId);
}
