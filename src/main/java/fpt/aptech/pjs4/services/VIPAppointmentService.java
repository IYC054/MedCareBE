package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.DTOs.AppointmentDetailDTO;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.VipAppointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VIPAppointmentService {
    VipAppointment createVIPAppointment(VipAppointment vipAppointment);
    List<VipAppointment> getDoctorByidDoctor(int doctorid);
    VipAppointment getVIPAppointmentById(int id);
    VipAppointment updateVipAppointmentStatusOnly(int id, String status);
    List<VipAppointment> getAllVIPAppointments();
    public AppointmentDetailDTO getvipAppointmentDetail(int appointmentId);
    List<VipAppointment> getAllVipAppointmentsByPatient(int patientId);
    void deleteAppointment(int id);
    VipAppointment updateDoctor(Integer appointmentId, Integer doctorId) ;
    String updateVipAppointment(Integer id, LocalDate workDate, LocalTime startTime, LocalTime endTime);
    boolean isWorkDateBooked(LocalDate workDate , int doctorId);
}
