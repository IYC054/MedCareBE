package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.DTOs.AppointmentDetailDTO;
import fpt.aptech.pjs4.entities.VipAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface VipAppointmentRepository extends JpaRepository<VipAppointment, Integer> {
    @Query("SELECT a FROM VipAppointment a WHERE a.patient.id = :patientId")
    List<VipAppointment> findAppointmentByPatientId(int patientId);
    @Query("SELECT a FROM VipAppointment a WHERE a.doctor.id = :doctorId")
    List<VipAppointment> findAppointmentByDoctorId(int doctorId);
    @Query("SELECT new fpt.aptech.pjs4.DTOs.AppointmentDetailDTO(pi.fullname, a_doctor.name, p.transactionCode, ap.workDate, ap.startTime, ap.endTime, p.status, ap.status, ap.bhyt) " +
            "FROM VipAppointment ap " +
            "JOIN ap.patientprofile pi " +
            "JOIN ap.doctor d " +
            "JOIN d.account a_doctor " +
            "JOIN ap.payments p " +
            "WHERE ap.id = :appointmentId")
    Optional<AppointmentDetailDTO> findVipAppointmentDetailById(@Param("appointmentId") int appointmentId);

    @Query("SELECT COUNT(a) > 0 FROM VipAppointment a " +
            "WHERE a.workDate = :workDate " +
            "AND a.doctor.id = :doctorId " +
            "AND :bookTime BETWEEN :startTime AND :endTime")
    boolean existsByWorkDate(
            @Param("workDate") LocalDate workDate,
            @Param("doctorId") int doctorId,
            @Param("bookTime") LocalTime bookTime,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);

}
