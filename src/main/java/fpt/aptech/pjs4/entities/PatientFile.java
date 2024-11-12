package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "patient_file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "prescription", length = 100)
    private String prescription;

    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Patient.class)
    @JoinColumn(name = "patients_id")
    private Patient patients;

    public PatientFile(Integer id) {
    }
}