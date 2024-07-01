package org.example.healthcare_management_system.repository;

import org.example.healthcare_management_system.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

}
