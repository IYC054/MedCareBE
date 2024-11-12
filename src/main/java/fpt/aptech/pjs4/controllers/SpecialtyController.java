package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Specialty;
import fpt.aptech.pjs4.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/specialty")
public class SpecialtyController {
    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@RequestBody Specialty specialty) {
        Specialty createdSpecialty = specialtyService.createSpecialty(specialty);
        return ResponseEntity.status(201).body(createdSpecialty);
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
}
