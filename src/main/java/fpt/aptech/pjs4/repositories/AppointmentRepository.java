package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.DTOs.AppointmentDetailDTO;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAppointmentByPatientId(int patientId);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Appointment> findAppointmentByDoctorId(int doctorId);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = :doctorId AND a.worktime.id = :worktimeId")
    long countByDoctorAndWorktime(@Param("doctorId") int doctorId, @Param("worktimeId") int worktimeId);

    @Query("SELECT new fpt.aptech.pjs4.DTOs.AppointmentDetailDTO(pi.fullname, a_doctor.name, p.transactionCode, dw.workDate, dw.startTime, dw.endTime, p.status) " +
            "FROM Appointment ap " +
            "JOIN ap.patientprofile pi " +
            "JOIN ap.doctor d " +
            "JOIN d.account a_doctor " +
            "JOIN ap.payments p " +
            "JOIN ap.worktime dw " +
            "WHERE ap.id = :appointmentId")
    Optional<AppointmentDetailDTO> findAppointmentDetailById(@Param("appointmentId") int appointmentId);


    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.worktime.workDate = :workDate")
    boolean existsByWorkDate(@Param("workDate") LocalDate workDate);


}
