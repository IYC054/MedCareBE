package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.AppointmentDetailDTO;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.repositories.AppointmentRepository;
import fpt.aptech.pjs4.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(int id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) {
        if (appointmentRepository.existsById(id)) {
            appointment.setId(id);
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    @Override
    public boolean checkslotApointment(int doctorid, int worktimeid) {
        long count = appointmentRepository.countByDoctorAndWorktime(
                doctorid,
                worktimeid
        );
        if (count >= 20) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentDetailDTO getAppointmentDetail(int appointmentId) {
        return appointmentRepository.findAppointmentDetailById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @Override
    public List<Appointment> getDoctorByidDoctor(int doctorid) {
        return appointmentRepository.findAppointmentByDoctorId(doctorid);
    }

    @Transactional
    @Override
    public Appointment updateAppointmentStatusOnly(int id, String status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment không tồn tại"));

        try {
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        } catch (Exception ex) {
            // Ghi lại chi tiết lỗi
            ex.printStackTrace();
            throw new RuntimeException("Lỗi khi lưu Appointment: " + ex.getMessage());
        }
    }


    @Override
    public List<Appointment> getAllAppointmentsByPatient(int patientId) {
        return appointmentRepository.findAppointmentByPatientId(patientId);
    }

    @Override
    public boolean isWorkDateBooked(LocalDate workDate,int doctorId) {
        return appointmentRepository.existsByWorkDate(workDate,doctorId);
    }
}
