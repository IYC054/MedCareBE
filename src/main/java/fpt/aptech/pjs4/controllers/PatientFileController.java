package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.PatientFileDTO;
import fpt.aptech.pjs4.DTOs.PatientFileDTOQuery;
import fpt.aptech.pjs4.entities.*;
import fpt.aptech.pjs4.services.*;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api/patientsfile")
public class PatientFileController {
    @Value("src/main/resources/static/image/")
    private String fileUpload;
    @Autowired
    ServletContext servletContext;
    private PatientFileService patientFilesService;
    @Autowired
    private PatientInformationService patientInformationService;
    @Autowired
    private FilesImageService filesImagesService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private VIPAppointmentService  vipAppointmentService;
    @Autowired
    private AppointmentService appointmentService;
    public PatientFileController(FilesImageService filesImagesService,PatientService patientsService,ServletContext servletContext, PatientFileService patientFilesService) {
        this.filesImagesService = filesImagesService;
        this.patientFilesService = patientFilesService;
        this.servletContext = servletContext;
        this.patientFilesService = patientFilesService;
    }

    @PostMapping
    public ResponseEntity<PatientFile> createPatientFile(
            @RequestParam("patients_profile_id") Integer patientsId,
            @RequestParam("doctors_id") Integer doctorId,
            @RequestParam(value = "appointment_id" ,required = false) Integer appointmentId,
//            @RequestParam(value = "vipappointment_id", required = false) Integer vipappointmentId,

            @RequestParam(value = "url_image", required = false) List<MultipartFile> files) {
        try {
            // Tạo thư mục upload nếu chưa tồn tại
            File uploadDir = new File(fileUpload);
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                throw new IOException("Không thể tạo thư mục upload: " + fileUpload);
            }

            // Lấy thông tin bệnh nhân, bác sĩ và cuộc hẹn
            PatientsInformation patient = patientInformationService.getPatientsInformationById(patientsId);
            if (patient == null) {
                throw new IllegalArgumentException("Bệnh nhân không tồn tại.");
            }

            Doctor doctor = doctorService.getDoctorById(doctorId);
            if (doctor == null) {
                throw new IllegalArgumentException("Bác sĩ không tồn tại.");
            }

            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            if (appointment == null) {
                throw new IllegalArgumentException("Cuộc hẹn không tồn tại.");
            }
//            VipAppointment vipappointment = vipAppointmentService.getVIPAppointmentById(vipappointmentId);
//            if (vipappointment == null) {
//                throw new IllegalArgumentException("Cuộc hẹn không tồn tại.");
//            }
            // Tạo `PatientFile`
            PatientFile patientFile = new PatientFile();
            patientFile.setPatientsInformation(patient);
            patientFile.setDoctor(doctor);
            patientFile.setAppointment(appointment);
//            patientFile.setVipappointment(vipappointment);
            PatientFile createdPatientFile = patientFilesService.createPatientFile(patientFile);

            // Xử lý tệp nếu có
            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    File destinationFile = new File(uploadDir, fileName);

                    // Copy file lên server
                    FileCopyUtils.copy(file.getBytes(), destinationFile);

                    // Lưu thông tin file vào DB
                    FilesImage fileImage = new FilesImage();
                    fileImage.setUrlImage(fileName);
                    fileImage.setPatientsFiles(createdPatientFile);
                    filesImagesService.createFilesImage(fileImage);
                }
            }

            return ResponseEntity.status(201).body(createdPatientFile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null); // Trả về lỗi do request không hợp lệ
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null); // Trả về lỗi khi upload file
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Trả về lỗi chung
        }
    }
//vip
@PostMapping("/vip-appointment")
public ResponseEntity<PatientFile> createPatientFileVIPapt(
        @RequestParam("patients_profile_id") Integer patientsId,
        @RequestParam("doctors_id") Integer doctorId,
        @RequestParam(value = "vipappointment_id", required = false) Integer vipappointmentId,

        @RequestParam(value = "url_image", required = false) List<MultipartFile> files) {
    try {
        // Tạo thư mục upload nếu chưa tồn tại
        File uploadDir = new File(fileUpload);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("Không thể tạo thư mục upload: " + fileUpload);
        }

        // Lấy thông tin bệnh nhân, bác sĩ và cuộc hẹn
        PatientsInformation patient = patientInformationService.getPatientsInformationById(patientsId);
        if (patient == null) {
            throw new IllegalArgumentException("Bệnh nhân không tồn tại.");
        }

        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            throw new IllegalArgumentException("Bác sĩ không tồn tại.");
        }


        VipAppointment vipappointment = vipAppointmentService.getVIPAppointmentById(vipappointmentId);
        if (vipappointment == null) {
            throw new IllegalArgumentException("Cuộc hẹn không tồn tại.");
        }
        // Tạo `PatientFile`
        PatientFile patientFile = new PatientFile();
        patientFile.setPatientsInformation(patient);
        patientFile.setDoctor(doctor);
        patientFile.setVipappointment(vipappointment);
        PatientFile createdPatientFile = patientFilesService.createPatientFile(patientFile);

        // Xử lý tệp nếu có
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                File destinationFile = new File(uploadDir, fileName);

                // Copy file lên server
                FileCopyUtils.copy(file.getBytes(), destinationFile);

                // Lưu thông tin file vào DB
                FilesImage fileImage = new FilesImage();
                fileImage.setUrlImage(fileName);
                fileImage.setPatientsFiles(createdPatientFile);
                filesImagesService.createFilesImage(fileImage);
            }
        }

        return ResponseEntity.status(201).body(createdPatientFile);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(400).body(null); // Trả về lỗi do request không hợp lệ
    } catch (IOException e) {
        return ResponseEntity.status(500).body(null); // Trả về lỗi khi upload file
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null); // Trả về lỗi chung
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<PatientFile> getPatientFileById(@PathVariable int id) {
        PatientFile patientFile = patientFilesService.getPatientFileById(id);
        return ResponseEntity.ok(patientFile);
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<List<PatientFile>> getPatientFileByIdProfileId(@PathVariable int id) {
        List<PatientFile> patientFile = patientFilesService.findAllPatientFilesByProfileId(id);
        return ResponseEntity.ok(patientFile);
    }
    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<PatientFile>> getPatientFileByDoctorId(@PathVariable int id) {
        List<PatientFile> patientFile = patientFilesService.getPatientFileByDoctorId(id);
        return ResponseEntity.ok(patientFile);
    }
    @GetMapping
    public ResponseEntity<List<PatientFile>> getAllPatientFiles() {
        List<PatientFile> patientFilesList = patientFilesService.getAllPatientFiles();
        return ResponseEntity.ok(patientFilesList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatientFile(@PathVariable int id, @RequestBody PatientFileDTO patientFileDTO) {
        System.out.println("Received ID: " + id);
        try {
            // Lấy PatientFile từ cơ sở dữ liệu
            PatientFile existingPatientFile = patientFilesService.getPatientFileById(id);
            if (existingPatientFile == null) {
                System.out.println("PatientFile with ID " + id + " not found");
                return ResponseEntity.status(404).body("PatientFile with ID " + id + " not found");
            }

            // Lấy Patient từ DTO
            PatientsInformation patient = patientInformationService.getPatientsInformationById(patientFileDTO.getPatientsInformationId());

            if (patient == null) {
                return ResponseEntity.badRequest().body("Patient with ID " + patientFileDTO.getPatientsInformationId() + " not found");
            }
            Doctor doctor = doctorService.getDoctorById(patientFileDTO.getDoctorId());
            // Cập nhật thông tin
            existingPatientFile.setDescription(patientFileDTO.getDescription());
            existingPatientFile.setPatientsInformation(patient);
            existingPatientFile.setDoctor(doctor);

            // Lưu vào cơ sở dữ liệu
            PatientFile updatedPatientFile = patientFilesService.updatePatientFile(id, existingPatientFile);
            return ResponseEntity.ok(updatedPatientFile);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
    @PutMapping("/description/{id}")
    public ResponseEntity<?> updatePatientFileDescription(@PathVariable int id, @RequestBody PatientFileDTO patientFileDTO) {
        System.out.println("Received ID: " + id);
        try {
            // Lấy PatientFile từ cơ sở dữ liệu
            PatientFile existingPatientFile = patientFilesService.getPatientFileById(id);
            if (existingPatientFile == null) {
                System.out.println("PatientFile with ID " + id + " not found");
                return ResponseEntity.status(404).body("PatientFile with ID " + id + " not found");
            }
            existingPatientFile.setDescription(patientFileDTO.getDescription());
            // Lưu vào cơ sở dữ liệu
            PatientFile updatedPatientFile = patientFilesService.updatePatientFile(id, existingPatientFile);
            return ResponseEntity.ok(updatedPatientFile);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientFile(@PathVariable int id) {
        FilesImage filesImage = filesImagesService.getFilesImageById(id);
        File image = new File(filesImage.getUrlImage());

        patientFilesService.deletePatientFile(id);
        return ResponseEntity.noContent().build();
    }

}