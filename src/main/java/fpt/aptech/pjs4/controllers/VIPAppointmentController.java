package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.VipAppointmentDTO;
import fpt.aptech.pjs4.entities.*;
import fpt.aptech.pjs4.services.DoctorService;
import fpt.aptech.pjs4.services.PatientInformationService;
import fpt.aptech.pjs4.services.PatientService;
import fpt.aptech.pjs4.services.VIPAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vip-appointments")
public class VIPAppointmentController {

    @Autowired
    private VIPAppointmentService vipAppointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientInformationService patientInformationService;
    // Lấy danh sách tất cả lịch hẹn VIP
    @GetMapping
    public ResponseEntity<List<VipAppointment>> getAllVIPAppointments() {
        List<VipAppointment> appointments = vipAppointmentService.getAllVIPAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Lấy thông tin lịch hẹn VIP theo ID
    @GetMapping("/{id}")
    public ResponseEntity<VipAppointment> getVIPAppointmentById(@PathVariable int id) {
        VipAppointment appointment = vipAppointmentService.getVIPAppointmentById(id);
        return appointment != null ? ResponseEntity.ok(appointment) : ResponseEntity.notFound().build();
    }
    @GetMapping("/doctors/{id}")
    public ResponseEntity<List<VipAppointment>> getAppointmentByDoctorId(@PathVariable int id) {
        List<VipAppointment> doctors = vipAppointmentService.getDoctorByidDoctor(id);
        return ResponseEntity.ok(doctors);
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<VipAppointment> updateAppointmentStatus(@PathVariable int id, @RequestBody Appointment appointment) {
        String status = appointment.getStatus();
        VipAppointment updatedAppointment = vipAppointmentService.updateVipAppointmentStatusOnly(id, status);
        return ResponseEntity.ok(updatedAppointment);
    }
    // Tạo mới lịch hẹn VIP
    @PostMapping
    public ResponseEntity<VipAppointment> createVIPAppointment(@RequestBody VipAppointmentDTO vipAppointmentDTO) {
        Patient patient = patientService.getPatientById(vipAppointmentDTO.getPatientId());
        Doctor doctor = doctorService.getDoctorById(vipAppointmentDTO.getDoctorId());
        PatientsInformation patientsInformation = patientInformationService.getPatientsInformationById(vipAppointmentDTO.getProfileId());
        VipAppointment appointment = new VipAppointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(vipAppointmentDTO.getStatus());
        appointment.setAmount(vipAppointmentDTO.getAmount());
        appointment.setStartTime(vipAppointmentDTO.getStartTime());
        appointment.setEndTime(vipAppointmentDTO.getEndTime());
        appointment.setType(vipAppointmentDTO.getType());
        appointment.setWorkDate(vipAppointmentDTO.getWorkDate());
        appointment.setPatientprofile(patientsInformation);
        VipAppointment createdAppointment = vipAppointmentService.createVIPAppointment(appointment);
        return ResponseEntity.ok(createdAppointment);
    }

    // Xóa lịch hẹn VIP theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVIPAppointment(@PathVariable int id) {
        vipAppointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<VipAppointment>> getAppointmentByPatientId(@PathVariable int id) {
        List<VipAppointment> appointment = vipAppointmentService.getAllVipAppointmentsByPatient(id);
        return ResponseEntity.ok(appointment);
    }
}
