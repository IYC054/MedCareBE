package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.VipAppointmentDTO;
import fpt.aptech.pjs4.entities.*;
import fpt.aptech.pjs4.services.*;
import fpt.aptech.pjs4.services.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    private final AccountService accountService;
    private final NotificationService notificationService;

    public VIPAppointmentController(AccountService accountService,
                                    NotificationService notificationService) {
        this.accountService = accountService;
        this.notificationService = notificationService;
    }

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
    public ResponseEntity<VipAppointment> updateAppointmentStatus(@PathVariable int id, @RequestBody Appointment appointment, @RequestParam String doctorEmail) {
        String status = appointment.getStatus();
        String doctortoken = accountService.getDoctorTokenByEmail(doctorEmail);

        if (doctortoken != null) {
            notificationService.sendNotification(doctortoken, "Thông báo",
                    "Đã có cuộc hẹn bị huỷ");
        }
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
        // Lấy token từ Firestore
        String userToken = accountService.getUserToken(vipAppointmentDTO.getFirestoreUserId());

        // Nếu có token, gửi thông báo
        if (userToken != null) {
            notificationService.sendNotification(userToken, "Đặt lịch thành công",
                    "Bạn đã đặt lịch khám VIP vào " + vipAppointmentDTO.getWorkDate() + " lúc "
                            + vipAppointmentDTO.getStartTime() + ". Vui lòng đến đúng giờ để được hỗ trợ tốt nhất!");
        }
        String doctortoken = accountService.getDoctorTokenByEmail(vipAppointmentDTO.getDoctorEmail());
        if (doctortoken != null) {
            notificationService.sendNotification(doctortoken, "Thông báo",
                    "Bạn có 1 cuộc hẹn vào " + vipAppointmentDTO.getStartTime() + "-" + vipAppointmentDTO.getEndTime());
        }
//        return ResponseEntity.status(201).body(createdAppointment);
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

    @PutMapping("/{id}/update-doctor")
    public ResponseEntity<VipAppointment> updateDoctor(
            @PathVariable Integer id,
            @RequestParam Integer doctorId) {

        VipAppointment updatedAppointment = vipAppointmentService.updateDoctor(id, doctorId);
        return ResponseEntity.ok(updatedAppointment);
    }

    @PutMapping("/{id}/update-time")
    public ResponseEntity<String> updateVipAppointment(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {

        LocalDate workDate = LocalDate.parse(request.get("workDate"));
        LocalTime startTime = LocalTime.parse(request.get("startTime"));
        LocalTime endTime = LocalTime.parse(request.get("endTime"));

        String message = vipAppointmentService.updateVipAppointment(id, workDate, startTime, endTime);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkWorkDate(
            @RequestParam LocalDate workDate,
            @RequestParam LocalTime bookTime,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime,
            @RequestParam int doctorId) {

        boolean exists = vipAppointmentService.isWorkDateBooked(workDate, bookTime, startTime, endTime, doctorId);
        return ResponseEntity.ok(exists);
    }

}
