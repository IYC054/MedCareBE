package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    @MessageMapping("/sendMessage")  // Endpoint mà client gửi tin nhắn đến
    public void sendMessage(Message message) {
        // Lưu tin nhắn vào cơ sở dữ liệu
        Message savedMessage = messageService.saveMessage(message);

        // Gửi tin nhắn đến người nhận qua WebSocket
        messagingTemplate.convertAndSendToUser(message.getReceiver().getId().toString(), "/queue/messages", savedMessage);
    }

    // API lấy tất cả tin nhắn giữa 2 user
    @GetMapping("/{senderId}/{receiverId}")
    public List<Message> getMessagesBetweenUsers(@PathVariable Integer senderId, @PathVariable Integer receiverId) {
        // Lấy thông tin tài khoản từ cơ sở dữ liệu (giả sử bạn có cách lấy account bằng ID)
        Account sender = new Account();  // Thay bằng cách lấy Account từ DB
        sender.setId(senderId);

        Account receiver = new Account();  // Thay bằng cách lấy Account từ DB
        receiver.setId(receiverId);

        // Lấy tất cả tin nhắn giữa 2 user
        return messageService.getMessagesBetweenUsers(sender, receiver);
    }
}