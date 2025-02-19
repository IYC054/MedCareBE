package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor_id;  // Người gửi tin nhắn

    @Column(name = "description", nullable = false)
    private String description;  // Nội dung tin nhắn
    @Column(name = "rate", nullable = false)
    private Integer rate;  // Nội dung tin nhắn
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient_id;  // Người nhận tin nhắn
}
