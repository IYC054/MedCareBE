package fpt.aptech.pjs4.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Integer sender;
    private Integer receiver;
    private String message;
    private String image;
    private LocalDateTime sentAt;
}
