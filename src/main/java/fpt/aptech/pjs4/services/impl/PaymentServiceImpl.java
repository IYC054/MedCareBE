package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Payment;
import fpt.aptech.pjs4.repositories.PaymentRepository;
import fpt.aptech.pjs4.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(int id) {
        return paymentRepository.findById(id).get();
    }

    @Override
    public Payment getPaymentbyDescription(String description) {
        return paymentRepository.findPaymentByTransactionDescription(description);
    }

    @Override
    public Payment updatePayment(int id,Payment payment) {
        if (paymentRepository.existsById(id)) {
            payment.setId(id);
            return paymentRepository.save(payment);
        }
        return null;
    }

    @Override
    public void deletePayment(int id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));



        // Sau đó xóa bản ghi Payment
        paymentRepository.deleteById(id);    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> filterPayments(LocalDate startDate, LocalDate endDate, String paymentId, String status) {
        // Chuyển LocalDate sang Instant với múi giờ UTC
        Instant startInstant = (startDate != null) ? startDate.atStartOfDay(ZoneId.systemDefault()).toInstant() : null;
        Instant endInstant = (endDate != null) ? endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant() : null;

        return paymentRepository.findFilteredPayments(startInstant, endInstant, paymentId, status);
    }
}
