package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospitals_id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "worktime")
    private Instant worktime;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "description", length = 250)
    private String description;
    @ManyToOne(targetEntity = Specialty.class)
    @JoinColumn(name = "specialties_id")
    private Specialty specialties;

}