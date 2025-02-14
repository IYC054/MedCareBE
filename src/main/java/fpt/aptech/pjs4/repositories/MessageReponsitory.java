package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageReponsitory extends JpaRepository<Message, Integer> {
    List<Message> findBySenderIdAndReceiverIdOrderBySentAtAsc(Integer senderId, Integer receiverId);
}
