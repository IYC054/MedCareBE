package fpt.aptech.projectapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "appointment_id", nullable = false)
    private Integer id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private fpt.aptech.projectapi.entities.Doctor doctor;

    @Column(name = "\"date\"")
    private LocalDate date;

    @Column(name = "\"time\"")
    private LocalTime time;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

}