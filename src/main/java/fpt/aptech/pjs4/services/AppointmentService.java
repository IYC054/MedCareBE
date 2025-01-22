package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Doctor;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);

    Appointment getAppointmentById(int id);

    List<Appointment> getAllAppointments();
    List<Appointment> getDoctorByidDoctor(int doctorid);
    Appointment updateAppointmentStatusOnly(int id, String status);
    Appointment updateAppointment(int id, Appointment appointment);

    void deleteAppointment(int id);

    List<Appointment> getAllAppointmentsByPatient(int patientId);
}
