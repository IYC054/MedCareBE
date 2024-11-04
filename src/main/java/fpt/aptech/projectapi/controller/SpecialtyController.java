package fpt.aptech.projectapi.controller;

import fpt.aptech.projectapi.entities.Specialty;
import fpt.aptech.projectapi.services.SpecialtiesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/specialty")
@AllArgsConstructor
public class SpecialtyController {
    private final SpecialtiesService specialtiesService;

    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@RequestBody Specialty specialty) {
        Specialty createdSpecialty = specialtiesService.createSpecialty(specialty);
        return ResponseEntity.status(201).body(createdSpecialty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable int id) {
        Specialty specialty = specialtiesService.getSpecialtyById(id);
        return ResponseEntity.ok(specialty);
    }

    @GetMapping
    public ResponseEntity<List<Specialty>> getAllSpecialties() {
        List<Specialty> specialtyList = specialtiesService.getAllSpecialties();
        return ResponseEntity.ok(specialtyList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable int id, @RequestBody Specialty specialty) {
        Specialty updatedSpecialty = specialtiesService.updateSpecialty(id, specialty);
        return ResponseEntity.ok(updatedSpecialty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable int id) {
        specialtiesService.deleteSpecialty(id);
        return ResponseEntity.noContent().build();
    }
}
