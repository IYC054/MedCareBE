package fpt.aptech.pjs4.DTOs;

import fpt.aptech.pjs4.entities.Message;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class MessageDto {
    private Long id;
    private String senderId;
    private String receiverId;
    private String content;
    private String sentAt;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.senderId = message.getSender().getId().toString();
        this.receiverId = message.getReceiver().getId().toString();
        this.content = message.getContent();
        this.sentAt = message.getSentAtFormatted();
    }
}
