package org.example.healthcare_management_system.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.UUID;


@Entity @NoArgsConstructor @Data @AllArgsConstructor
public class DoctorSpeciality {
    @Id @GeneratedValue
    private UUID specialityId;

    @ManyToOne
    @JoinColumn()  @OnDelete(action = OnDeleteAction.CASCADE)
    private User doctorId;

    private String speciality;
    private Integer FeesAmount;
}

