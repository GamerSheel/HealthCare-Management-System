package org.example.healthcare_management_system.service;

import org.example.healthcare_management_system.model.Payment;
import org.example.healthcare_management_system.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(LocalDateTime date) {
        return paymentRepository.findById(date);
    }

    public Payment updatePayment(LocalDateTime date, Payment payment ) {
        if (paymentRepository.existsById(date)) {

            payment.setPaymentDate(date); // Ensure the provided user has the correct ID
            return paymentRepository.save(payment);
        }
        return null; // Or throw an exception indicating the user was not found
    }

    public void deletePayment(LocalDateTime date) {
        paymentRepository.deleteById(date);
    }

}
