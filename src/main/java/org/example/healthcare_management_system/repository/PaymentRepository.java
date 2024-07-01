package org.example.healthcare_management_system.repository;
import org.example.healthcare_management_system.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface PaymentRepository extends JpaRepository<Payment, LocalDateTime> {
}
