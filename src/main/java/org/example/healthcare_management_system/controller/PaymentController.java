package org.example.healthcare_management_system.controller;

import org.example.healthcare_management_system.model.Payment;
import org.example.healthcare_management_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.savePayment(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{paymentDate}")
    public Payment getPaymentById(@PathVariable LocalDateTime paymentDate) {
        Optional<Payment> paymentOptional = paymentService.getPaymentById(paymentDate);
        return paymentOptional.orElse(null); // Or handle the case when user is not found
    }

    @PutMapping("/{paymentDate}")
    public Payment updatePayment(@PathVariable LocalDateTime paymentDate, @RequestBody Payment payment) {
        return paymentService.updatePayment(paymentDate, payment);
    }

    @DeleteMapping("/{paymentDate}")
    public void deletePayment(@PathVariable LocalDateTime paymentDate) {
        paymentService.deletePayment(paymentDate);
    }
}