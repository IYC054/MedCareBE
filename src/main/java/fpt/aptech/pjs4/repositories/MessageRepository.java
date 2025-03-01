package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender) ORDER BY m.sentAt ASC")
    List<Message> findChatHistory(@Param("sender") Account sender, @Param("receiver") Account receiver);
}
