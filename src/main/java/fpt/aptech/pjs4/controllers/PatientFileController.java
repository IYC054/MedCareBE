package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.FilesImage;
import fpt.aptech.pjs4.entities.PatientFile;
import fpt.aptech.pjs4.services.FilesImageService;
import fpt.aptech.pjs4.services.PatientFileService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    ServletContext servletContext;
    @Autowired
    private PatientFileService patientFileService;
    @Autowired
    private FilesImageService filesImageService;

    private final Path rootlocation = Paths.get("src\\main\\resources\\static\\uploads");
    @PostMapping
    public ResponseEntity<PatientFile> createPatientFile(@RequestParam("patientFile") PatientFile patientFile,
                                                         @RequestParam("url_image") MultipartFile file) {
        try {
            System.out.println();

            String uploadRootPath = this.rootlocation.toAbsolutePath().toString();
            File uploadDir = new File(uploadRootPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filename = file.getOriginalFilename();
            File serverfile = new File(uploadDir.getAbsoluteFile() + File.separator + filename);

            PatientFile createdPatientFile = patientFileService.createPatientFile(patientFile);

            uploadPatientFile(file);


            FilesImage fileImage = new FilesImage();
            PatientFile p = new PatientFile(createdPatientFile.getId());
            fileImage.setUrlImage(serverfile.toString());
            fileImage.setPatientsFiles(p);
            filesImageService.createFilesImage(fileImage);

            return ResponseEntity.status(201).body(createdPatientFile);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating patient file", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientFile> getPatientFileById(@PathVariable int id) {
        PatientFile patientFile = patientFileService.getPatientFileById(id);
        return ResponseEntity.ok(patientFile);
    }

    @GetMapping
    public ResponseEntity<List<PatientFile>> getAllPatientFiles() {
        List<PatientFile> patientFilesList = patientFileService.getAllPatientFiles();
        return ResponseEntity.ok(patientFilesList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientFile> updatePatientFile(@PathVariable int id, @RequestBody PatientFile patientFile) {
        PatientFile updatedPatientFile = patientFileService.updatePatientFile(id, patientFile);
        return ResponseEntity.ok(updatedPatientFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientFile(@PathVariable int id) {
        FilesImage filesImage = filesImageService.getFilesImageById(id);
        File image = new File(filesImage.getUrlImage());

        patientFileService.deletePatientFile(id);
        return ResponseEntity.noContent().build();
    }
    public String uploadPatientFile(MultipartFile file) {
//        String uploadRootPath = servletContext.getRealPath("upload");
        String uploadRootPath = this.rootlocation.toAbsolutePath().toString();
        File uploadDir = new File(uploadRootPath);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }
        try {
            String filename = file.getOriginalFilename();
            File serverfile = new File(uploadDir.getAbsoluteFile() + File.separator + filename);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverfile));
            stream.write(file.getBytes());
            stream.close();
            System.out.println("#####" + serverfile);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return "Upload success";
    }
}
