package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Payment;
import fpt.aptech.pjs4.repositories.PaymentRepository;
import fpt.aptech.pjs4.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Payment updatePayment(int id,Payment payment) {
        if (paymentRepository.existsById(id)) {
            payment.setId(id);
            return paymentRepository.save(payment);
        }
        return null;
    }

    @Override
    public void deletePayment(int id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
