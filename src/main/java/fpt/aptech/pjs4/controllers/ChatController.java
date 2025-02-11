package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.repositories.MessageReponsitory;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageReponsitory messageRepository;
    private final AccountRepository accountRepository;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageReponsitory messageRepository, AccountRepository accountRepository) {
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }


    @PostMapping("/{to}")
    public void sendMessage(@RequestBody Message message, @PathVariable String to) {
        // Kiểm tra nếu sender hoặc receiver là null
        if (message.getSender() == null || message.getReceiver() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender or Receiver cannot be null");
        }

        // Lấy thông tin sender và receiver từ database
        Account sender = accountRepository.findById(message.getSender().getId()).orElse(null);
        Account receiver = accountRepository.findById(message.getReceiver().getId()).orElse(null);

        if (sender != null && receiver != null) {
            // Đặt thời gian gửi tin nhắn với độ chính xác đến giây
            message.setSent(LocalDateTime.now());

            // Lưu tin nhắn vào database
            messageRepository.save(message);

            // Gửi tin nhắn đến receiver thông qua messagingTemplate
            messagingTemplate.convertAndSendToUser(to, "/queue/messages", message);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender or Receiver not found");
        }
    }


    @GetMapping("/history/{senderId}/{receiverId}")
    public List<Message> getChatHistory(@PathVariable Integer senderId, @PathVariable Integer receiverId) {
        // Lấy thông tin sender và receiver từ database
        Account sender = accountRepository.findById(senderId).orElse(null);
        Account receiver = accountRepository.findById(receiverId).orElse(null);

        // Nếu cả sender và receiver đều tồn tại
        if (sender != null && receiver != null) {
            // Trả về tất cả tin nhắn giữa sender và receiver, không phân biệt thứ tự sender và receiver
            List<Message> messagesFromSenderToReceiver = messageRepository.findBySenderAndReceiver(sender, receiver);
            List<Message> messagesFromReceiverToSender = messageRepository.findBySenderAndReceiver(receiver, sender);

            // Kết hợp cả 2 danh sách tin nhắn
            messagesFromSenderToReceiver.addAll(messagesFromReceiverToSender);

            // Sắp xếp theo thời gian gửi (từ cũ đến mới)
            return messagesFromSenderToReceiver.stream()
                    .sorted(Comparator.comparing(Message::getSent))
                    .collect(Collectors.toList());
        }

        return List.of();
    }

}
