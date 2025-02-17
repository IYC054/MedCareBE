package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.PaymentDTOQuery;
import fpt.aptech.pjs4.entities.Payment;
import fpt.aptech.pjs4.repositories.PaymentRepository;
import fpt.aptech.pjs4.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Payment createPayment(Payment payment) {
        if (payment.getTransactionCode() == null) {
            String newTransactionCode;
            do {
                newTransactionCode = "MEDCARE" + (int) (Math.random() * 10000000);
            } while (paymentRepository.existsByTransactionCode(newTransactionCode)); // Kiểm tra trùng
            payment.setTransactionCode(newTransactionCode);
        }
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
    public List<Payment> getPaymentsByCurrentDate() {
        return paymentRepository.findPaymentsByCurrentDate();
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
    public Payment findPaymentByTransactionCode(String transcode) {
        return paymentRepository.findPaymentByTransactionCode(transcode);
    }

    @Override
    public List<Payment> filterPayments(LocalDate startDate, LocalDate endDate, String transactionCode, String status) {
        // Chuyển LocalDate sang Instant với múi giờ UTC
        Instant startInstant = (startDate != null) ? startDate.atStartOfDay(ZoneId.systemDefault()).toInstant() : null;
        Instant endInstant = (endDate != null) ? endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant() : null;

        return paymentRepository.findFilteredPayments(startInstant, endInstant, transactionCode, status);
    }

    @Override
    public List<Payment> findPaymentByAppointmentId(Integer appointmentId) {
        return paymentRepository.findPaymentByAppointment_Id(appointmentId);
    }

    @Override
    public List<Payment> findPaymentByVipAppointmentId(Integer vipappointmentId) {
        return paymentRepository.findPaymentByVipAppointmentId(vipappointmentId);
    }

    @Override
    public List<PaymentDTOQuery> findAllPaymentByPatientId(int patientId) {
        return paymentRepository.findAllPaymentByPatientId(patientId);
    }
}
