package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties({"messagesSent", "messagesReceived"})
    private Account sender;  // Người gửi tin nhắn

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonIgnoreProperties({"messagesSent", "messagesReceived"})
    private Account receiver;  // Người nhận tin nhắn


    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentAt = LocalDateTime.now();
    public String getSentAtFormatted() {
        return sentAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
