package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Feedback;
import fpt.aptech.pjs4.entities.SendMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendMailReponsitory extends JpaRepository<SendMail, Long> {

}
