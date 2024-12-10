package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
