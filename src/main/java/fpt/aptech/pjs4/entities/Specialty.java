package fpt.aptech.pjs4.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "specialties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Doctor.class)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}