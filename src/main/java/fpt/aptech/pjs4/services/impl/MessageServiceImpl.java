package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.repositories.MessageReponsitory;
import fpt.aptech.pjs4.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageReponsitory messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message saveMessage(Account sender, Account receiver, String messageText, String imageUrl) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(messageText);
        message.setImage(imageUrl);
        message.setSent(LocalDateTime.now()); // Set the current time when the message is sent

        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }
}

