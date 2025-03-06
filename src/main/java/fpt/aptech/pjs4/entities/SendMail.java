package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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


    private String phone;
    @Column(name = "sender_email", updatable = false)
    private String sender_email;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Size(max = 255)
    @Column(name = "fullname")
    private String fullname;

    @Size(max = 255)
    @Column(name = "message")
    private String message;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "NEW";
        }
    }
}
