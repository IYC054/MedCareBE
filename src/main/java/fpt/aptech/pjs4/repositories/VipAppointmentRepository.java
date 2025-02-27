package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.DTOs.AppointmentDetailDTO;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.VipAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VipAppointmentRepository extends JpaRepository<VipAppointment, Integer> {
    @Query("SELECT a FROM VipAppointment a WHERE a.patient.id = :patientId")
    List<VipAppointment> findAppointmentByPatientId(int patientId);
    @Query("SELECT a FROM VipAppointment a WHERE a.doctor.id = :doctorId")
    List<VipAppointment> findAppointmentByDoctorId(int doctorId);
    @Query("SELECT new fpt.aptech.pjs4.DTOs.AppointmentDetailDTO(pi.fullname, a_doctor.name, p.transactionCode, ap.workDate, ap.startTime, ap.endTime, p.status, ap.status) " +
            "FROM VipAppointment ap " +
            "JOIN ap.patientprofile pi " +
            "JOIN ap.doctor d " +
            "JOIN d.account a_doctor " +
            "JOIN ap.payments p " +
            "WHERE ap.id = :appointmentId")
    Optional<AppointmentDetailDTO> findVipAppointmentDetailById(@Param("appointmentId") int appointmentId);

}
