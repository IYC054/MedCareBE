package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Feedback;
import fpt.aptech.pjs4.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    // POST feedback for an account
    @PostMapping("/{accountId}")
    public ResponseEntity<Feedback> submitFeedback(
            @PathVariable Integer accountId,
            @RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackService.saveFeedback(accountId, feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    // GET all feedbacks for an account
    @GetMapping("/{accountId}")
    public ResponseEntity<List<Feedback>> getFeedbacks(@PathVariable Integer accountId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByAccountId(accountId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

}
