package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.DTOs.PaymentDTOQuery;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPayment(int id);
    Payment updatePaymentStatusOnly(int id, String status);
    Payment getPaymentbyDescription(String description);
    List<Payment> getPaymentsByCurrentDate();
    Payment updatePayment(int id,Payment payment);
    void deletePayment(int id);
    List<Payment> getAllPayments();
    public Payment findPaymentByTransactionCode(String transcode);
    public List<Payment> filterPayments(LocalDate startDate, LocalDate endDate,
                                        String transactionCode, String status);
    List<Payment> findPaymentByAppointmentId(Integer appointmentId);
    List<Payment> findPaymentByVipAppointmentId(Integer vipappointmentId);
    List<PaymentDTOQuery> findAllPaymentByPatientId(int patientId);

}
