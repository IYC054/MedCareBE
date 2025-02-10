package fpt.aptech.pjs4.repositories;


import fpt.aptech.pjs4.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageReponsitory extends JpaRepository<Message, Integer> {
}
