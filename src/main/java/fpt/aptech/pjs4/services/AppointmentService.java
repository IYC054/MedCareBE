package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);

    Appointment getAppointmentById(int id);

    List<Appointment> getAllAppointments();

    Appointment updateAppointment(int id, Appointment appointment);

    void deleteAppointment(int id);
}
