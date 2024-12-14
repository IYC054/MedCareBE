package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.PaymentDTO;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Payment;
import fpt.aptech.pjs4.services.AppointmentService;
import fpt.aptech.pjs4.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AppointmentService appointmentService;

    // Create Payment
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO paymentDTO) {
        Appointment appointment = appointmentService.getAppointmentById(paymentDTO.getAppointmentId());
        if (appointment == null) {
            return ResponseEntity.status(404).body(null); // Appointment không tồn tại
        }

        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        payment.setTransactionDescription(paymentDTO.getTransactionDescription());
        payment.setAppointment(appointment);

        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.status(201).body(createdPayment);
    }

    // Get Payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        Payment payment = paymentService.getPayment(id);
        if (payment == null) {
            return ResponseEntity.status(404).body(null); // Nếu không tìm thấy Payment
        }
        return ResponseEntity.ok(payment);
    }

    // Get All Payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> paymentList = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentList);
    }

    // Update Payment
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody PaymentDTO paymentDTO) {
        Payment existingPayment = paymentService.getPayment(id);
        if (existingPayment == null) {
            return ResponseEntity.status(404).body(null); // Nếu không tìm thấy Payment
        }

        Appointment appointment = appointmentService.getAppointmentById(paymentDTO.getAppointmentId());
        if (appointment == null) {
            return ResponseEntity.status(404).body(null); // Nếu không tìm thấy Appointment
        }

        existingPayment.setAmount(paymentDTO.getAmount());
        existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());
        existingPayment.setStatus(paymentDTO.getStatus());
        existingPayment.setTransactionDescription(paymentDTO.getTransactionDescription());
        existingPayment.setAppointment(appointment);

        Payment updatedPayment = paymentService.updatePayment(id,existingPayment);
        return ResponseEntity.ok(updatedPayment);
    }

    // Delete Payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
