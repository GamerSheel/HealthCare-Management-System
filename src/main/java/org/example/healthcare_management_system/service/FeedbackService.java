package org.example.healthcare_management_system.service;

import org.example.healthcare_management_system.model.Feedback;
import org.example.healthcare_management_system.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Optional<Feedback> getFeedbackById(UUID feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    public Feedback updateFeedback(Feedback feedback,UUID feedbackId) {
        if(feedbackRepository.existsById(feedbackId)) {
            return feedbackRepository.save(feedback);
        }
        return null;
    }

    public void deleteFeedbackById(UUID feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
