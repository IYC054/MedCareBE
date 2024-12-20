package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByAccountId(Integer accountId);
    boolean existsByAccountIdAndMessage(Integer accountId, String message);
}
