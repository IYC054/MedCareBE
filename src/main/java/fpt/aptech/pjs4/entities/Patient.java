package fpt.aptech.pjs4.entities;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "descriptions", length = 250)
    private String descriptions;
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne(targetEntity = Doctor.class)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}