package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Hospital;
import fpt.aptech.pjs4.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/hospital")
public class HospitalController {
    @Value("src/main/resources/static/image/")
    private String fileUpload;
    @Autowired
    private HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<Hospital> createHospital(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("worktime") Instant worktime,
            @RequestParam("phone") String phone,
            @RequestParam("description") String description,
            @RequestPart(value = "file", required = false) MultipartFile image) throws IOException {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            Path uploadDir = Paths.get("src/main/resources/static/image");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path destinationPath = uploadDir.resolve(uniqueFileName);
            Files.write(destinationPath, image.getBytes());
            imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image/")
                    .path(uniqueFileName)
                    .toUriString();

        }

        Hospital hospital = new Hospital();
        hospital.setName(name);
        hospital.setAddress(address);
        hospital.setWorktime(worktime);
        hospital.setPhone(phone);
        hospital.setDescription(description);
        hospital.setUrlImage(imageUrl);


        Hospital createdHospital = hospitalService.createHospital(hospital);
        return ResponseEntity.status(201).body(createdHospital);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable int id) {
        Hospital hospital = hospitalService.getHospitalById(id);
        return  ResponseEntity.ok(hospital);
    }

    @GetMapping
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        return ResponseEntity.ok(hospitals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable int id, @RequestBody Hospital hospital) {
        Hospital updatedHospital = hospitalService.updateHospital(id, hospital);
        return ResponseEntity.ok(updatedHospital);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable int id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.noContent().build();
    }
}
