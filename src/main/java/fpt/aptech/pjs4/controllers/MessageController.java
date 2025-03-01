package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.repositories.MessageRepository;
import fpt.aptech.pjs4.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final AccountRepository accountRepository;

    // Constructor injection (Best Practice)
    public MessageController(MessageRepository messageRepository, SimpMessagingTemplate messagingTemplate, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.accountRepository = accountRepository;
    }

    // Xử lý gửi tin nhắn qua WebSocket
    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload Message message) {
        if (message.getReceiver() == null || message.getReceiver().getId() == null) {
            throw new IllegalArgumentException("Receiver must not be null");
        }

        message.setSentAt(LocalDateTime.now()); // Đảm bảo timestamp đúng
        Message savedMessage = messageRepository.save(message);

        messagingTemplate.convertAndSendToUser(
                message.getReceiver().getId().toString(),
                "/queue/messages",
                savedMessage
        );
    }


    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Integer senderId, @PathVariable Integer receiverId) {
        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        Account receiver = accountRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        List<Message> messages = messageRepository.findChatHistory(sender, receiver);
        return ResponseEntity.ok(messages);
    }
}