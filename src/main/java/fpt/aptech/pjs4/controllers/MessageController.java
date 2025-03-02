package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.MessageDto;
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
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final AccountRepository accountRepository;
    @Autowired
    private SimpUserRegistry userRegistry;

    public MessageController(MessageRepository messageRepository, SimpMessagingTemplate messagingTemplate, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.accountRepository = accountRepository;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Message chatMessage) {
        System.out.println("📩 Nhận tin nhắn từ FE: " + chatMessage);

        Account sender = accountRepository.findById(chatMessage.getSender().getId())
                .orElseThrow(() -> new RuntimeException("❌ Không tìm thấy người gửi"));

        Account receiver = accountRepository.findById(chatMessage.getReceiver().getId())
                .orElseThrow(() -> new RuntimeException("❌ Không tìm thấy người nhận"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(chatMessage.getContent());
        message.setSentAt(LocalDateTime.now());
        messageRepository.save(message);

        MessageDto messageDto = new MessageDto(message);
        String destination = "/user" + receiver.getId() + "/queue/messages";

        System.out.println("📡 Gửi tới: " + destination);
        System.out.println("📨 Nội dung gửi đi: " + messageDto);
        messagingTemplate.convertAndSend(receiver.getId() + "/queue/messages", messageDto);
    }


    // Gửi tin nhắn qua WebSocket và lưu vào DB
//    @MessageMapping("/sendMessage")
//    public void sendMessage(@Payload Message message) {
//        if (message.getSender() == null) {
//            throw new IllegalArgumentException("Sender must not be null");
//        }
//        if (message.getSender().getId() == null) {
//            throw new IllegalArgumentException("Sender ID must not be null");
//        }
//        if (message.getReceiver() == null) {
//            throw new IllegalArgumentException("Receiver must not be null");
//        }
//        if (message.getReceiver().getId() == null) {
//            throw new IllegalArgumentException("Receiver ID must not be null");
//        }
//
//
//        message.setSentAt(LocalDateTime.now());
//        System.out.println("📅 SentAt before saving: " + message.getSentAt());
//        Message savedMessage = messageRepository.save(message);
//
//        String destination = "/queue/messages";
//        String receiverId = message.getReceiver().getId().toString();
//        System.out.println("📤 Sending message to user: " + receiverId + " at destination: " + destination);
//        try {
//            messagingTemplate.convertAndSendToUser(receiverId, destination, new MessageDto(savedMessage));
//            System.out.println("✅ Message sent successfully!");
//            System.out.println("📤 Preparing to send message...");
//            System.out.println("👤 Receiver ID: " + receiverId);
//            System.out.println("📌 Destination: " + destination);
//            System.out.println("📨 Message content: " + new MessageDto(savedMessage));
//        } catch (Exception e) {
//            System.err.println("❌ Error sending message: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }


    // API lấy lịch sử tin nhắn giữa 2 người dùng
    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Integer senderId, @PathVariable Integer receiverId) {
        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        Account receiver = accountRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        List<Message> messages = messageRepository.findChatHistory(sender, receiver);
        return ResponseEntity.ok(messages);
    }

//    @PostMapping("/messages")
//    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDTO) {
//        Optional<Account> sender = accountRepository.findById(messageDTO.getSe().getId());
//        Optional<Account> receiver = accountRepository.findById(messageDTO.getReceiverId().getId());
//
//        if (sender.isEmpty() || receiver.isEmpty()) {
//            return ResponseEntity.badRequest().body("Sender or receiver not found");
//        }
//
//        Message message = new Message();
//        message.setSender(sender.get());
//        message.setReceiver(receiver.get());
//        message.setContent(messageDTO.getContent());
//        message.setSentAt(LocalDateTime.now());
//
//        messageRepository.save(message);
//        return ResponseEntity.ok("Message sent successfully!");
//    }

}
