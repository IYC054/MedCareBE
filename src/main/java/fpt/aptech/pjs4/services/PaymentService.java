package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPayment(int id);
    Payment getPaymentbyDescription(String description);
    List<Payment> getPaymentsByCurrentDate();
    Payment updatePayment(int id,Payment payment);
    void deletePayment(int id);
    List<Payment> getAllPayments();

    public List<Payment> filterPayments(LocalDate startDate, LocalDate endDate,
                                        String paymentId, String status);
}
