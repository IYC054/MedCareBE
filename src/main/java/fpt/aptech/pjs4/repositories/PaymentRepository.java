package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT t FROM Payment t WHERE t.transactionDescription = :transactionDescription")
    public Payment findPaymentByTransactionDescription(String transactionDescription);
}
