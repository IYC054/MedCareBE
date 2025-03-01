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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;  // Người gửi tin nhắn

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiver;  // Người nhận tin nhắn

//    @Column(name = "message_text", nullable = false)
//    private String message;  // Nội dung tin nhắn


//    @Size(max = 100)
//    @Column(name = "image_url", length = 100)
//    private String image;  // URL của hình ảnh (nếu có)

    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();  // Thời gian gửi tin nhắn
}
