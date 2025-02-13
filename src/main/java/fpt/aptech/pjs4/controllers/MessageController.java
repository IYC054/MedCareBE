package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.request.MessageDTO;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private final MessageService messageService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @MessageMapping("/chat.sendMessage") // Sử dụng WebSocket
    @SendTo("/topic/messages") // Gửi tin nhắn đến tất cả người đăng ký nhận tin nhắn
    public Message sendMessage(@RequestBody MessageDTO messageDTO) {
        Account sender = accountRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Account receiver = accountRepository.findById(messageDTO.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message savedMessage = messageService.saveMessage(
                sender, receiver, messageDTO.getMessageText(), messageDTO.getImageUrl()
        );

        return savedMessage; // Tin nhắn gửi đến WebSocket subscriber
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
        if (!messageService.getMessageById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
