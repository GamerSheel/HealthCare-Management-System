package org.example.healthcare_management_system.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
//@Data automatically generates below annotations
//@Getter @Setter @ToString
public class Payment {
    @Id @GeneratedValue
    private UUID paymentId;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS")
    private LocalDateTime paymentDate ;

    @ManyToOne
    @JoinColumn() @OnDelete(action = OnDeleteAction.CASCADE)
    private User patientId;

    @ManyToOne
    @JoinColumn()  @OnDelete(action = OnDeleteAction.CASCADE)
    private Appointment appointmentId;

    private String amount;
    private String status;
}

