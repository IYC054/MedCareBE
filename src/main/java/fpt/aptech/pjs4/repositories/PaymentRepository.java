package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT t FROM Payment t WHERE t.transactionDescription = :transactionDescription")
    public Payment findPaymentByTransactionDescription(String transactionDescription);
    @Query(value = "SELECT * FROM Payments WHERE CAST(transaction_date AS DATE) = CAST(GETDATE() AS DATE)", nativeQuery = true)
    List<Payment> findPaymentsByCurrentDate();
}
