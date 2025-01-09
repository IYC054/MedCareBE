package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT t FROM Payment t WHERE t.transactionDescription = :transactionDescription")
    public Payment findPaymentByTransactionDescription(String transactionDescription);
    @Query(value = "SELECT * FROM Payments WHERE CAST(transaction_date AS DATE) = CAST(GETDATE() AS DATE)", nativeQuery = true)
    List<Payment> findPaymentsByCurrentDate();
    @Query("SELECT p FROM Payment p " +
            "WHERE (:startDate IS NULL OR p.transactionDate >= :startDate) " +
            "AND (:endDate IS NULL OR p.transactionDate <= :endDate) " +
            "AND (:transactionCode IS NULL OR p.transactionCode = :transactionCode) " +
            "AND (:status IS NULL OR p.status = :status)")
    List<Payment> findFilteredPayments(
            @Param("startDate") Instant startTime,
            @Param("endDate") Instant endTime,
            @Param("transactionCode") String transactionCode,
            @Param("status") String status
    );
    @Query("SELECT p FROM Payment p WHERE p.transactionCode = :transcode")
    public Payment findPaymentByTransactionCode(@Param("transcode") String transcode);

    public List<Payment> findPaymentByAppointment_Id(int appointmentId);
}