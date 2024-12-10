package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.PaymentDTO;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Patient;
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
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO paymentDTO) {
        Appointment appointment = appointmentService.getAppointmentById(paymentDTO.getAppointmentId());
        if (appointment == null) {
            return ResponseEntity.status(404).body(null); // Appointment không tồn tại
        }
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setBankTransfer(paymentDTO.getBankTransfer());
        payment.setStatus(paymentDTO.getStatus());
        payment.setAppointment(appointment);

        // Lưu Payment
        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.status(201).body(createdPayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        Payment payment = paymentService.getPayment(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> paymentList = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody PaymentDTO paymentDTO) {
        // Kiểm tra xem Payment có tồn tại không
        Payment existingPayment = paymentService.getPayment(id);
        if (existingPayment == null) {
            return ResponseEntity.status(404).body(null); // Nếu không tìm thấy Payment
        }

        // Lấy Appointment từ appointmentId trong PaymentDTO
        Appointment appointment = appointmentService.getAppointmentById(paymentDTO.getAppointmentId());
        if (appointment == null) {
            return ResponseEntity.status(404).body(null); // Nếu không tìm thấy Appointment
        }

        // Cập nhật Payment
        existingPayment.setAmount(paymentDTO.getAmount());
        existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());
        existingPayment.setBankTransfer(paymentDTO.getBankTransfer());
        existingPayment.setStatus(paymentDTO.getStatus());
        existingPayment.setAppointment(appointment); // Gán lại Appointment cho Payment

        // Lưu Payment đã cập nhật
        Payment updatedPayment = paymentService.updatePayment(id, existingPayment);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
