//package fpt.aptech.pjs4.controllers;
//
//import fpt.aptech.pjs4.DTOs.MessageDto;
//import fpt.aptech.pjs4.entities.Account;
//import fpt.aptech.pjs4.entities.Message;
//import fpt.aptech.pjs4.repositories.AccountRepository;
//import fpt.aptech.pjs4.repositories.MessageReponsitory;
//import org.springframework.http.HttpStatus;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/chat")
//public class ChatController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final MessageReponsitory messageRepository;
//    private final AccountRepository accountRepository;
//
//    public ChatController(SimpMessagingTemplate messagingTemplate, MessageReponsitory messageRepository, AccountRepository accountRepository) {
//        this.messagingTemplate = messagingTemplate;
//        this.messageRepository = messageRepository;
//        this.accountRepository = accountRepository;
//    }
//
//
//
//    @MessageMapping("/sendMessage") // Gửi tin nhắn từ client
//    public void sendMessage(@Payload MessageDto messageDTO) {
//        // Gửi tin nhắn đến người nhận theo "/user/{receiverId}/queue/messages"
//        messagingTemplate.convertAndSend("/user/" + messageDTO.getReceiver() + "/queue/messages", messageDTO);
//    }
//
//}
