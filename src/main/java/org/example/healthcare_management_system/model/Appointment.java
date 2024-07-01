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
public class Appointment {
    @Id @GeneratedValue
    private UUID appointmentId;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate appointmentDate;

    @ManyToOne @JoinColumn() @OnDelete(action = OnDeleteAction.CASCADE)
    private User patientId;

    @ManyToOne @JoinColumn()  @OnDelete(action = OnDeleteAction.CASCADE)
    private User doctorId;

    private String status;
    private String issue;
    private String age;
    private String forWhichPerson;
    private LocalTime startTime;
    private LocalTime endTime;
}
