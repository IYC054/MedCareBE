package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Payment;

import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPayment(int id);
    Payment updatePayment(int id,Payment payment);
    void deletePayment(int id);
    List<Payment> getAllPayments();
}
