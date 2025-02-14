package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.repositories.MessageReponsitory;
import fpt.aptech.pjs4.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageReponsitory messageRepository;

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesBetweenUsers(Account sender, Account receiver) {
        return messageRepository.findBySenderIdAndReceiverIdOrderBySentAtAsc(sender.getId(), receiver.getId());
    }
}

