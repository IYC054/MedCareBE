package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.PaymentDTO;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Payment;
import fpt.aptech.pjs4.services.AppointmentService;
import fpt.aptech.pjs4.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AppointmentService appointmentService;
    @PostMapping("/confirm-payment")
    public ResponseEntity<?> confirmPayment(
            @RequestParam int resultCode,
            @RequestParam String amount,
            @RequestParam String orderInfo,
            @RequestParam String trans_code) {

        try {
            BigDecimal amountDecimal = new BigDecimal(amount);
            System.out.println("Received params: resultCode=" + resultCode + ", amount=" + amountDecimal + ", orderInfo=" + orderInfo);
            Appointment appointment = appointmentService.getAppointmentById(1);
            Instant now = Instant.now();
            ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            LocalDateTime localDateTime = LocalDateTime.ofInstant(now, zoneId);
            Instant transactionDate = localDateTime.atZone(zoneId).toInstant();
            if (resultCode == 0) {
                // Xử lý thanh toán thành công
                Payment payment = new Payment();
                payment.setAmount(amountDecimal);
                payment.setPaymentMethod("Momo");
                payment.setTransactionDate(transactionDate);
                payment.setAppointment(appointment);
                payment.setTransactionCode(trans_code);
                payment.setStatus("Chờ xử lý");
                payment.setTransactionDescription(orderInfo);
                paymentService.createPayment(payment);
                return ResponseEntity.ok("Payment confirmed successfully.");
            } else {
                return ResponseEntity.status(400).body("Payment failed");
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("Invalid amount format");
        }
    }

    // Create Payment
    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        Appointment appointment = appointmentService.getAppointmentById(paymentDTO.getAppointmentId());
        if (appointment == null) {
            return ResponseEntity.status(404).body("Không tồn tại giao dịch này");
        }
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, zoneId);
        Instant transactionDate = localDateTime.atZone(zoneId).toInstant();
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        payment.setTransactionCode(paymentDTO.getTransactionCode());
        payment.setStatus("Chờ xử lý");
        payment.setTransactionDescription(paymentDTO.getTransactionDescription());
        payment.setTransactionDate(transactionDate);
        payment.setAppointment(appointment);

        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.status(201).body(createdPayment);
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelPayment(@PathVariable int id) {
        try {
            Payment payment = paymentService.getPayment(id);
            System.out.println(payment.getStatus());
            if(payment == null) {
                return ResponseEntity.status(404).body("Không tồn tại giao dịch này");
            }
            payment.setStatus("Đang chờ xử lý hoàn tiền");
            paymentService.updatePayment(id, payment);
            return ResponseEntity.status(200).body(payment);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi xử lý giao dịch: " + e.getMessage());
        }
    }
    @Scheduled(cron = "0 0 23 * * *")
    public void runAt11PM() {
        List<Payment> payments = paymentService.getPaymentsByCurrentDate();
        for (Payment payment : payments) {
            if(payment.getStatus().contentEquals("Đang Hold")) {
                payment.setStatus("Thanh toán thành công");
                paymentService.updatePayment(payment.getId(), payment);
            }
        }
        System.out.println("Đã chạy vào lúc 11h tối: " + java.time.LocalDateTime.now());
    }
    // Get Payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        Payment payment = paymentService.getPayment(id);
        if (payment == null) {
            return ResponseEntity.status(404).body(null);
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
            return ResponseEntity.status(404).body(null);
        }

        Appointment appointment = appointmentService.getAppointmentById(paymentDTO.getAppointmentId());
        if (appointment == null) {
            return ResponseEntity.status(404).body(null);
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
        @GetMapping("/transcode/{code}")
    public ResponseEntity<List<Payment>> getAllPayments(@PathVariable String code) {
        List<Payment> paymentcode = paymentService.findPaymentByTransactionCode(code);
        return ResponseEntity.ok(paymentcode);
    }
    @GetMapping("/filter")
    public List<Payment> filterPaymentWithParams(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,

            @RequestParam(required = false) String transactionCode,

            @RequestParam(required = false) String status) {


        return paymentService.filterPayments(startDate, endDate, transactionCode, status);
    }
}
