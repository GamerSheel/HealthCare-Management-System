package org.example.healthcare_management_system.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.UUID;


@Entity @NoArgsConstructor @Data @AllArgsConstructor
public class DoctorDegree {
    @Id @GeneratedValue
    private UUID degreeId;

    @ManyToOne
    @JoinColumn()  @OnDelete(action = OnDeleteAction.CASCADE)
    private User doctorId;

    private String institute;
    private String degree;
    private Integer year;
}

