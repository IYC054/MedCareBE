package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.entities.Appointment;

import java.util.List;

public interface AppointmentsService {
    Appointment createAppointment(Appointment appointment);
    Appointment getAppointmentById(int id);
    List<Appointment> getAllAppointments();
    Appointment updateAppointment(int id, Appointment appointment);
    void deleteAppointment(int id);
}
