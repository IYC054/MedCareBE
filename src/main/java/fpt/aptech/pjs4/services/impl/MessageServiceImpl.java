package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.repositories.MessageReponsitory;
import fpt.aptech.pjs4.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageReponsitory messageReponsitory;

    @Autowired
    public MessageServiceImpl(MessageReponsitory messageReponsitory) {
        this.messageReponsitory = messageReponsitory;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageReponsitory.findAll();
    }

    @Override
    public Optional<Message> getMessageById(Integer id) {
        return messageReponsitory.findById(id);
    }
    @Override
    public Message saveMessage(Account sender, Account receiver, String messageText, String imageUrl) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(messageText);
        message.setImage(imageUrl);
        message.setSent(LocalDateTime.now());

        // Lưu tin nhắn vào cơ sở dữ liệu
        return messageReponsitory.save(message);
    }

    @Override
    public void deleteMessage(Integer id) {
        messageReponsitory.deleteById(id);
    }
}
