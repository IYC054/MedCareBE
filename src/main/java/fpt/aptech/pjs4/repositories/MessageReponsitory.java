package fpt.aptech.pjs4.repositories;


import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageReponsitory extends JpaRepository<Message, Integer> {
    List<Message> findBySenderAndReceiver(Account sender, Account receiver);
}
