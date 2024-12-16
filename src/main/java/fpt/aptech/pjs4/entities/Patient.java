package fpt.aptech.pjs4.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @JsonProperty("doctor_id")
    public Integer getDoctorId() {
        return doctor != null ? doctor.getId() : null;
    }
    @JsonProperty("account_id")
    public Integer getPatientId() {
        return account != null ? account.getId() : null;
    }
}