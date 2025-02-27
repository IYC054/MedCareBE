package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // API gửi tin nhắn
    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public Message sendNotification(Message notification) {
        return notification;
    }

}