package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "SendMails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String fullname;
    private String phone;
    @Column(name = "sender_email", updatable = false)
    private String sender_email;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "NEW";
        }
    }
}
