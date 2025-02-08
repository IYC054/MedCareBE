package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.FilesImage;
import fpt.aptech.pjs4.services.FilesImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/filesimage")
public class FilesImageController {
    @Value("src/main/resources/static/image/")
    private String fileUpload;
    @Autowired
    private FilesImageService filesImageService;

    // Tạo mới FilesImage
    @PostMapping
    public ResponseEntity<List<FilesImage>> createFilesImages(@RequestParam("url_image") List<MultipartFile> files, @RequestParam("patientfile_id") int patientfile_id) {
        List<FilesImage> createdFilesImages = filesImageService.createFileWithImage(files, patientfile_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilesImages);
    }

    // Lấy FilesImage theo ID
    @GetMapping("/{id}")
    public ResponseEntity<FilesImage> getFilesImageById(@PathVariable int id) {
        FilesImage filesImage = filesImageService.getFilesImageById(id);
        if (filesImage != null) {
            return ResponseEntity.ok(filesImage);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("patient_profile/{id}")
    public ResponseEntity<List<FilesImage>> getFilesImageByPatientProfileId(@PathVariable int id) {
        List<FilesImage> filesImage = filesImageService.getAllFilesImagesbyPatientFileId(id);
        if (filesImage != null) {
            return ResponseEntity.ok(filesImage);
        }
        return ResponseEntity.notFound().build();
    }
    // Lấy tất cả FilesImages
    @GetMapping
    public ResponseEntity<List<FilesImage>> getAllFilesImages() {
        List<FilesImage> filesImages = filesImageService.getAllFilesImages();
        return ResponseEntity.ok(filesImages);
    }

    // Cập nhật FilesImage
    @PutMapping("/{id}")
    public ResponseEntity<FilesImage> updateFilesImage(@PathVariable int id, @RequestBody FilesImage filesImage) {
        FilesImage updatedFilesImage = filesImageService.updateFilesImage(id, filesImage);
        if (updatedFilesImage != null) {
            return ResponseEntity.ok(updatedFilesImage);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa FilesImage theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilesImage(@PathVariable int id) {
        filesImageService.deleteFilesImage(id);
        return ResponseEntity.noContent().build();
    }
}
