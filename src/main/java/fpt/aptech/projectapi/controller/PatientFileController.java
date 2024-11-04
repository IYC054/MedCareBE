package fpt.aptech.projectapi.controller;

import fpt.aptech.projectapi.entities.FilesImage;
import fpt.aptech.projectapi.entities.PatientFile;
import fpt.aptech.projectapi.services.PatientFilesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patientsfile")
@AllArgsConstructor
public class PatientFileController {
    private PatientFilesService patientFilesService;

    @PostMapping
    public ResponseEntity<PatientFile> createPatientFile(@RequestParam PatientFile patientFile,
                                                         @RequestParam("url_image") String photo) {
        PatientFile createdPatientFile = patientFilesService.createPatientFile(patientFile);
        FilesImage fileImage = new FilesImage();
        PatientFile p = new PatientFile(createdPatientFile.getId());
        fileImage.setUrlImage(photo);
        fileImage.setPatientsFiles(p);
        return ResponseEntity.status(201).body(createdPatientFile);
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
        patientFilesService.deletePatientFile(id);
        return ResponseEntity.noContent().build();
    }
}
