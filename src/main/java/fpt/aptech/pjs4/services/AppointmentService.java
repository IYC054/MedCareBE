package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.DTOs.AppointmentDetailDTO;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Doctor;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);

    Appointment getAppointmentById(int id);

    List<Appointment> getAllAppointments();

    List<Appointment> getDoctorByidDoctor(int doctorid);

    Appointment updateAppointmentStatusOnly(int id, String status);

    Appointment updateAppointment(int id, Appointment appointment);

    boolean checkslotApointment(int doctorid, int worktimeid);

    void deleteAppointment(int id);

    public AppointmentDetailDTO getAppointmentDetail(int appointmentId);

    List<Appointment> getAllAppointmentsByPatient(int patientId);

    boolean isWorkDateBooked(LocalDate workDate);
}
