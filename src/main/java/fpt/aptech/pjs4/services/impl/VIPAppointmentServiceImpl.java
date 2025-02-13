package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.VipAppointment;
import fpt.aptech.pjs4.repositories.AppointmentRepository;
import fpt.aptech.pjs4.repositories.VipAppointmentRepository;
import fpt.aptech.pjs4.services.AppointmentService;
import fpt.aptech.pjs4.services.VIPAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VIPAppointmentServiceImpl implements VIPAppointmentService {
    @Autowired
    private VipAppointmentRepository appointmentRepository;
    @Override
    public VipAppointment createVIPAppointment(VipAppointment vipAppointment) {
        return appointmentRepository.save(vipAppointment);
    }

    @Override
    public VipAppointment getVIPAppointmentById(int id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<VipAppointment> getAllVIPAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }


}
