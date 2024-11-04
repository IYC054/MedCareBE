package fpt.aptech.projectapi.services.impl;

import fpt.aptech.projectapi.entities.Account;
import fpt.aptech.projectapi.entities.Appointment;
import fpt.aptech.projectapi.repository.AccountsRepository;
import fpt.aptech.projectapi.repository.AppointmentsRepository;
import fpt.aptech.projectapi.services.AccountServices;
import fpt.aptech.projectapi.services.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServicesImpl implements AppointmentsService {
    @Autowired
    private AppointmentsRepository appointmentsRepository;


    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentsRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(int id) {
        return appointmentsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentsRepository.findAll();
    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) {
        if (appointmentsRepository.existsById(id)) {
            appointment.setId(id);
            return appointmentsRepository.save(appointment);
        }
        return null;
    }

    @Override
    public void deleteAppointment(int id) {
        appointmentsRepository.deleteById(id);
    }
}
