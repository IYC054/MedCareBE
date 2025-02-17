package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Rating;
import fpt.aptech.pjs4.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rates")
public class RateController {
    @Autowired
    private RateService rateService;

    // 📌 Lấy danh sách tất cả ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = rateService.getAllRate();
        return ResponseEntity.ok(ratings);
    }

    // 📌 Lấy chi tiết rating theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable int id) {
        Rating rating = rateService.getRateById(id);
        return ResponseEntity.ok(rating);
    }

    // 📌 Thêm mới rating
    @PostMapping
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
        Rating savedRating = rateService.rateDoctor(rating);
        return ResponseEntity.ok(savedRating);
    }

    // 📌 Cập nhật rating
    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable int id, @RequestBody Rating ratingDetails) {
        Rating ratingOptional = rateService.getRateById(id);
        ratingOptional.setRate(ratingDetails.getRate());
        ratingOptional.setDescription(ratingDetails.getDescription());
        ratingOptional.setPatient_id(ratingDetails.getPatient_id());
        ratingOptional.setDoctor_id(ratingDetails.getDoctor_id());
        Rating updatedRating = rateService.updateRate(id, ratingOptional);
        return ResponseEntity.ok(updatedRating);

    }

    // 📌 Xóa rating theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable int id) {
        rateService.deleteRate(id);
        return ResponseEntity.noContent().build();
    }

}
