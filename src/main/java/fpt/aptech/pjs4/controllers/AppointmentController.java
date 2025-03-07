package fpt.aptech.pjs4.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import fpt.aptech.pjs4.DTOs.AppointmentDTO;
import fpt.aptech.pjs4.configs.QRCodeService;
import fpt.aptech.pjs4.entities.*;
import fpt.aptech.pjs4.services.*;
import fpt.aptech.pjs4.services.impl.NotificationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorWorkingHourService doctorWorkingHourService;
    @Autowired
    private PatientInformationService patientInformationService;

    private final AccountService accountService;
    private final NotificationService notificationService;

    public AppointmentController(AccountService accountService,
                                 NotificationService notificationService) {
        this.accountService = accountService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDTO appointmentrequest) {
        try {
            Patient patient = patientService.getPatientById(appointmentrequest.getPatientId());
            Doctor doctor = doctorService.getDoctorById(appointmentrequest.getDoctorId());
            Doctorworking worktime = doctorWorkingHourService.getWorkingHour(appointmentrequest.getWorktimeId());
            PatientsInformation patientsInformation = patientInformationService.getPatientsInformationById(appointmentrequest.getPatientProfileId());
            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setType(appointmentrequest.getType());
            appointment.setStatus(appointmentrequest.getStatus());
            appointment.setAmount(appointmentrequest.getAmount());
            appointment.setWorktime(worktime);
            appointment.setPatientprofile(patientsInformation);
            appointment.setBhyt(appointmentrequest.getBhyt());
            Appointment createdAppointment = appointmentService.createAppointment(appointment);


            // Lấy token từ Firestore
            String userToken = accountService.getUserToken(appointmentrequest.getFirestoreUserId());

            // Nếu có token, gửi thông báo
            if (userToken != null) {
                notificationService.sendNotification(userToken, "Đặt lịch thành công",
                        "Bạn đã đặt lịch khám thường vào " + worktime.getWorkDate());
            }
            String doctortoken = accountService.getDoctorTokenByEmail(appointmentrequest.getDoctorEmail());
            if (doctortoken != null) {
                notificationService.sendNotification(doctortoken, "Thông báo",
                        "Bạn có 1 cuộc hẹn vào " + worktime.getWorkDate());
            }

            return ResponseEntity.status(201).body(createdAppointment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentByPatientId(@PathVariable int id) {
        List<Appointment> appointment = appointmentService.getAllAppointmentsByPatient(id);
        return ResponseEntity.ok(appointment);
    }
    @GetMapping("/checkslot/{doctorid}/{worktimeid}")
    public ResponseEntity<Boolean> checkSlotAppointment(@PathVariable int doctorid, @PathVariable int worktimeid) {
        boolean checkslotApointment = appointmentService.checkslotApointment(doctorid, worktimeid);
        if(checkslotApointment) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
    @GetMapping("/doctors/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentByDoctorId(@PathVariable int id) {
        List<Appointment> doctors = appointmentService.getDoctorByidDoctor(id);
        return ResponseEntity.ok(doctors);
    }
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        return ResponseEntity.ok(updatedAppointment);
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable int id, @RequestBody Appointment appointment, @RequestParam String doctorEmail) {
        String status = appointment.getStatus();
        String doctortoken = accountService.getDoctorTokenByEmail(doctorEmail);
        if (doctortoken != null) {
            notificationService.sendNotification(doctortoken, "Thông báo",
                    "Đã có cuộc hẹn bị huỷ");
        }
        Appointment updatedAppointment = appointmentService.updateAppointmentStatusOnly(id, status);
        return ResponseEntity.ok(updatedAppointment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/generate-qrcode")
    public void generateQRCode(@RequestParam int appointmentId, HttpServletResponse response, @RequestParam boolean isVIP) throws Exception {
        String serverIp = getServerIp();
        // Tạo URL chứa thông tin cuộc hẹn
        String qrCodeData = "";
        if(isVIP){
            qrCodeData = "http://" + serverIp +":8080/vip-appointment/details?VipAppointmentId=" + appointmentId;

        }else{
            qrCodeData = "http://" + serverIp +":8080/appointment/details?appointmentId=" + appointmentId;
        }

        // Tạo QR Code
        int width = 300;
        int height = 300;
        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, width, height, hintMap);

        // Thiết lập response header để trả về hình ảnh QR
        response.setContentType("image/png");
        try {
            MatrixToImageWriter.writeToStream(matrix, "PNG", response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error writing QR Code to output stream", e);
        }
    }
    public String getServerIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress(); // Trả về địa chỉ IP của máy chủ
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "localhost"; // Trả về localhost nếu có lỗi
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkWorkDate(@RequestParam LocalDate workDate,@RequestParam int doctorId) {
        boolean exists = appointmentService.isWorkDateBooked(workDate , doctorId);
        return ResponseEntity.ok(exists);
    }

}
