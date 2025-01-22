package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.PatientFileDTO;
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

    public PatientFileController(FilesImageService filesImagesService,PatientService patientsService,ServletContext servletContext, PatientFileService patientFilesService) {
        this.filesImagesService = filesImagesService;
        this.patientFilesService = patientFilesService;
        this.servletContext = servletContext;
        this.patientFilesService = patientFilesService;
    }

    @PostMapping
    public ResponseEntity<PatientFile> createPatientFile(@ModelAttribute PatientFile patientFile,
                                                         @RequestParam("patients_profile_id") Integer patientsId,
                                                         @RequestParam("doctors_id") Integer doctorId,
                                                         @RequestParam("url_image") List<MultipartFile> files) {
        try {
            File uploadDir = new File(fileUpload);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            PatientsInformation patient = patientInformationService.getPatientsInformationById(patientsId);
            Doctor doctor = doctorService.getDoctorById(doctorId);

            patientFile.setPatientsInformation(patient);
            PatientFile createdPatientFile = patientFilesService.createPatientFile(patientFile);
            patientFile.setDoctor(doctor);
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                File destinationFile = new File(uploadDir, fileName);

                FileCopyUtils.copy(file.getBytes(), destinationFile);

                FilesImage fileImage = new FilesImage();
                fileImage.setUrlImage(fileName);
                fileImage.setPatientsFiles(createdPatientFile);

                filesImagesService.createFilesImage(fileImage);
            }
            return ResponseEntity.status(201).body(createdPatientFile);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating patient file", e);
        }
    }
  
    @GetMapping("/{id}")
    public ResponseEntity<PatientFile> getPatientFileById(@PathVariable int id) {
        PatientFile patientFile = patientFilesService.getPatientFileById(id);
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