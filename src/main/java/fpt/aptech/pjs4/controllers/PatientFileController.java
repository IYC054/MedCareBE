package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.FilesImage;
import fpt.aptech.pjs4.entities.Patient;
import fpt.aptech.pjs4.entities.PatientFile;
import fpt.aptech.pjs4.services.FilesImageService;
import fpt.aptech.pjs4.services.PatientFileService;
import fpt.aptech.pjs4.services.PatientService;
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
    private PatientService patientsService;
    @Autowired
    private FilesImageService filesImagesService;


    public PatientFileController(FilesImageService filesImagesService,PatientService patientsService,ServletContext servletContext, PatientFileService patientFilesService) {
        this.filesImagesService = filesImagesService;
        this.patientFilesService = patientFilesService;
        this.servletContext = servletContext;
        this.patientFilesService = patientFilesService;
    }

    @PostMapping
    public ResponseEntity<PatientFile> createPatientFile(@ModelAttribute PatientFile patientFile,
                                                         @RequestParam("patients_id") Integer patientsId,
                                                         @RequestParam("url_image") List<MultipartFile> files) {
        try {
            File uploadDir = new File(fileUpload);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            Patient patient = patientsService.getPatientById(patientsId);
            patientFile.setPatients(patient);
            PatientFile createdPatientFile = patientFilesService.createPatientFile(patientFile);

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

    @GetMapping
    public ResponseEntity<List<PatientFile>> getAllPatientFiles() {
        List<PatientFile> patientFilesList = patientFilesService.getAllPatientFiles();
        return ResponseEntity.ok(patientFilesList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientFile> updatePatientFile(@PathVariable int id, @RequestBody PatientFile patientFile) {
        PatientFile updatedPatientFile = patientFilesService.updatePatientFile(id, patientFile);
        return ResponseEntity.ok(updatedPatientFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientFile(@PathVariable int id) {
        FilesImage filesImage = filesImagesService.getFilesImageById(id);
        File image = new File(filesImage.getUrlImage());

        patientFilesService.deletePatientFile(id);
        return ResponseEntity.noContent().build();
    }

}