package fpt.aptech.pjs4.DTOs;

import lombok.Data;

@Data
public class MessageDto {
    private Integer senderId;
    private Integer receiverId;
    private String messageText;
    private String imageUrl;
}
