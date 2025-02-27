package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.CvImage;
import fpt.aptech.pjs4.entities.FilesImage;
import fpt.aptech.pjs4.services.CvImageService;
import fpt.aptech.pjs4.services.FilesImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/cvimage")
public class CvImageController {
    @Value("src/main/resources/static/image/")
    private String fileUpload;
    @Autowired
    private CvImageService filesImageService;

    // Tạo mới FilesImage
    @PostMapping
    public ResponseEntity<List<CvImage>> createCvImages(@RequestParam("url_image") List<MultipartFile> files, @RequestParam("doctorfile_id") int patientfile_id) {
        List<CvImage> createdCvImages = filesImageService.createFileWithImage(files, patientfile_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCvImages);
    }

    // Lấy CvImage theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CvImage> getCvImageById(@PathVariable int id) {
        CvImage filesImage = filesImageService.getCvImageById(id);
        if (filesImage != null) {
            return ResponseEntity.ok(filesImage);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("doctor_id/{id}")
    public ResponseEntity<List<CvImage>> getCvImageByPatientProfileId(@PathVariable int id) {
        List<CvImage> filesImage = filesImageService.getAllCvImagesbyPatientFileId(id);
        if (filesImage != null) {
            return ResponseEntity.ok(filesImage);
        }
        return ResponseEntity.notFound().build();
    }
    // Lấy tất cả CvImages
    @GetMapping
    public ResponseEntity<List<CvImage>> getAllCvImages() {
        List<CvImage> filesImages = filesImageService.getAllCvImages();
        return ResponseEntity.ok(filesImages);
    }

    // Cập nhật CvImage
    @PutMapping("/{id}")
    public ResponseEntity<CvImage> updateCvImage(@PathVariable int id, @RequestBody CvImage filesImage) {
        CvImage updatedCvImage = filesImageService.updateCvImage(id, filesImage);
        if (updatedCvImage != null) {
            return ResponseEntity.ok(updatedCvImage);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa CvImage theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCvImage(@PathVariable int id) {
        filesImageService.deleteCvImage(id);
        return ResponseEntity.noContent().build();
    }
}
