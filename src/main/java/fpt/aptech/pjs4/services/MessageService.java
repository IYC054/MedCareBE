package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    List<Message> getAllMessages();

    Optional<Message> getMessageById(Integer id);

    Message saveMessage(Account sender, Account receiver, String messageText, String imageUrl);

    void deleteMessage(Integer id);
}
