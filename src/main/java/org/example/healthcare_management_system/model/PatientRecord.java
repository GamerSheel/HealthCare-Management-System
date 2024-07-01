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
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
//@Data automatically generates below annotations
//@Getter @Setter @ToString
public class PatientRecord {
    @Id @GeneratedValue
    private UUID patientRecordId;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS")
    private LocalDateTime recordDate;

    @ManyToOne
    @JoinColumn() @OnDelete(action = OnDeleteAction.CASCADE)
    private User patientId;

    @ManyToOne
    @JoinColumn()  @OnDelete(action = OnDeleteAction.CASCADE)
    private User doctorId;

    @ManyToOne
    @JoinColumn()  @OnDelete(action = OnDeleteAction.CASCADE)
    private Appointment appointmentId;

    private String notes;
    private String prescription;
    private String description;
    private String medicalRecordUploadPath;
}

