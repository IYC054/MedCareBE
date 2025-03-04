package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.AppointmentDetailDTO;
import fpt.aptech.pjs4.entities.Doctor;
import fpt.aptech.pjs4.entities.VipAppointment;
import fpt.aptech.pjs4.repositories.DoctorRepository;
import fpt.aptech.pjs4.repositories.VipAppointmentRepository;
import fpt.aptech.pjs4.services.VIPAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VIPAppointmentServiceImpl implements VIPAppointmentService {
    @Autowired
    private VipAppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public VipAppointment createVIPAppointment(VipAppointment vipAppointment) {
        return appointmentRepository.save(vipAppointment);
    }

    @Override
    public List<VipAppointment> getDoctorByidDoctor(int doctorid) {
        return appointmentRepository.findAppointmentByDoctorId(doctorid);
    }

    @Override
    public VipAppointment getVIPAppointmentById(int id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public VipAppointment updateVipAppointmentStatusOnly(int id, String status) {
        VipAppointment vipAppointmentappointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment không tồn tại"));

        try {
            vipAppointmentappointment.setStatus(status);
            return appointmentRepository.save(vipAppointmentappointment);
        } catch (Exception ex) {
            // Ghi lại chi tiết lỗi
            ex.printStackTrace();
            throw new RuntimeException("Lỗi khi lưu Appointment: " + ex.getMessage());
        }
    }

    @Override
    public List<VipAppointment> getAllVIPAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public AppointmentDetailDTO getvipAppointmentDetail(int appointmentId) {
        return appointmentRepository.findVipAppointmentDetailById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @Override
    public List<VipAppointment> getAllVipAppointmentsByPatient(int patientId) {
        return appointmentRepository.findAppointmentByPatientId(patientId);
    }

    @Override
    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public VipAppointment updateDoctor(Integer appointmentId, Integer doctorId) {
        Optional<VipAppointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);

        if (appointmentOpt.isPresent() && doctorOpt.isPresent()) {
            VipAppointment appointment = appointmentOpt.get();
            appointment.setDoctor(doctorOpt.get());
            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Appointment or Doctor not found");
        }
    }

    @Override
    public String updateVipAppointment(Integer id, LocalDate workDate, LocalTime startTime, LocalTime endTime) {
        Optional<VipAppointment> optionalAppointment = appointmentRepository.findById(id);

        if (optionalAppointment.isEmpty()) {
            return "Lịch hẹn không tồn tại!";
        }

        VipAppointment appointment = optionalAppointment.get();

        // Cập nhật giá trị mới
        appointment.setWorkDate(workDate);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);

        appointmentRepository.save(appointment);

        return "Cập nhật thành công!";
    }

    @Override
    public boolean isWorkDateBooked(LocalDate workDate, int doctorId) {
        return appointmentRepository.existsByWorkDate(workDate, doctorId);
    }

}
