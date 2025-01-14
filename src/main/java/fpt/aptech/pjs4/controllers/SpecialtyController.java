package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Specialty;
import fpt.aptech.pjs4.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/specialty")
public class SpecialtyController {




    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping("/create")
    public ResponseEntity<Specialty> createSpecialty(
            @RequestPart("image") MultipartFile image,
            @RequestPart("name") String name,
            @RequestPart("description") String description
    ) throws IOException {
        // Tạo thư mục nếu chưa tồn tại
        Path uploadDir = Paths.get("src/main/resources/static/specialty");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Lưu file
        String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path destinationPath = uploadDir.resolve(uniqueFileName);
        Files.write(destinationPath, image.getBytes());


        // Tạo URL hoàn chỉnh cho hình ảnh
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/specialty/image/")
                .path(uniqueFileName)
                .toUriString();
        // Tạo và lưu Specialty
        Specialty specialty = new Specialty();
        specialty.setName(name);
        specialty.setDescription(description);
        specialty.setImage(imageUrl); // Lưu URL vào database

        Specialty savedSpecialty = specialtyService.createSpecialty(specialty);
        System.out.println("File saved at: " + destinationPath);

        return ResponseEntity.ok(savedSpecialty);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable int id) {
        Specialty specialty = specialtyService.getSpecialtyById(id);
        return ResponseEntity.ok(specialty);
    }

    @GetMapping
    public ResponseEntity<List<Specialty>> getAllSpecialties() {
        List<Specialty> specialtyList = specialtyService.getAllSpecialties();
        return ResponseEntity.ok(specialtyList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable int id, @RequestBody Specialty specialty) {
        Specialty updatedSpecialty = specialtyService.updateSpecialty(id, specialty);
        return ResponseEntity.ok(updatedSpecialty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable int id) {
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Specialty>> getSpecialtiesByDoctorId(@PathVariable Integer doctorId) {
        List<Specialty> specialties = specialtyService.getSpecialtiesByDoctorId(doctorId);
        return ResponseEntity.ok(specialties);
    }
}
