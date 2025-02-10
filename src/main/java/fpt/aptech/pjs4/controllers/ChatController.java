package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.MessageDto;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Message;
import fpt.aptech.pjs4.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    // WebSocket endpoint for real-time messaging
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(String messageText, Integer senderId, Integer receiverId, String imageUrl) {
        // Retrieve Accounts from the database using senderId and receiverId
        Account sender = new Account(); // Fetch sender from DB using senderId
        sender.setId(senderId);

        Account receiver = new Account(); // Fetch receiver from DB using receiverId
        receiver.setId(receiverId);

        // Save message in the database
        Message message = messageService.saveMessage(sender, receiver, messageText, imageUrl);

        // Send the message to a specific WebSocket destination
        messagingTemplate.convertAndSend("/topic/public", message);  // Sends the message to all subscribers
    }

    // API endpoint for sending message through HTTP (for testing or integration with frontend)
    @PostMapping("/send")
    public Message sendMessageApi(@RequestBody MessageDto messageDto) {
        // Retrieve Accounts from the database using senderId and receiverId
        Account sender = new Account(); // Fetch sender from DB using messageDto.getSenderId()
        sender.setId(messageDto.getSenderId());

        Account receiver = new Account(); // Fetch receiver from DB using messageDto.getReceiverId()
        receiver.setId(messageDto.getReceiverId());

        // Save message into the database
        Message message = messageService.saveMessage(sender, receiver, messageDto.getMessageText(), messageDto.getImageUrl());

        // Return the saved message
        return message;
    }
}
