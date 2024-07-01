package org.example.healthcare_management_system.controller;

import org.example.healthcare_management_system.model.Feedback;
import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.example.healthcare_management_system.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/feedbackform")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Map<String,Object> FeedbackFormData) {
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(UUID.randomUUID());
        feedback.setRecordDate(LocalDateTime.now());
        //feedback.setFeedbackText();
        feedback.setFeedbackText((String)FeedbackFormData.get("Feedback"));
        feedback.setFeedbackForWhom((String)FeedbackFormData.get("FeedbackforWhom"));

        String userEmail = (String)FeedbackFormData.get("patientId");
        User user=customUserDetailsService.getUserDetailsById(userEmail);

        if(user==null){
            return ResponseEntity.notFound().build();
        }

        feedback.setPatientId(user);
        feedbackService.saveFeedback(feedback);

        return ResponseEntity.ok().body(feedback);
    }

    @GetMapping("/allFeedbacks")
    public List<Feedback> getAllFeedbacks() {return feedbackService.getAllFeedback();}

    @GetMapping("/{feedbackId}")
    public Feedback getFeedbackById(@PathVariable UUID feedbackId){
        Optional<Feedback> feedback = feedbackService.getFeedbackById(feedbackId);
        return feedback.orElse(null);
    }

    @PutMapping("/{feedbackId}")
    public Feedback updateFeedback(@RequestBody Feedback feedback,@PathVariable UUID feedbackId) {
        return feedbackService.updateFeedback(feedback,feedbackId);
    }

    @DeleteMapping("/{feedbackId}")
    public void deleteFeedback(@PathVariable UUID feedbackId) {feedbackService.deleteFeedbackById(feedbackId);}
}
