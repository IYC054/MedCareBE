package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;

import java.util.List;


public interface MessageService {

    Message saveMessage(Message message);

    List<Message> getMessagesBetweenUsers(Account sender, Account receiver);
}
