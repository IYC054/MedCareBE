package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "patient_files")
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

    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "patients_id")
    private Patient patients;
    @JsonProperty("patients_id")
    public Integer getPatientsId() {
        return patients != null ? patients.getId() : null;
    }

}