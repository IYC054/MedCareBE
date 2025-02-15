package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

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
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;  // Người gửi tin nhắn

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;  // Người nhận tin nhắn

    @Column(name = "message_text", nullable = false)
    private String message;  // Nội dung tin nhắn

    @Column(name = "image_url", length = 250)
    private String image;
    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();  // Thời gian gửi tin nhắn
}
