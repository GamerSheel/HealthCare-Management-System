package org.example.healthcare_management_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Feedback {
    @Id @GeneratedValue
    private UUID feedbackId;

//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS")
    private LocalDateTime recordDate;

    @ManyToOne @JoinColumn() @OnDelete(action = OnDeleteAction.CASCADE)
    private User patientId;

    private String feedbackText;
    
    private String feedbackForWhom;
}
