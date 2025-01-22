package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "Patient_files")
public class PatientFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Nationalized
    @Column(name = "description", length = 100)
    private String description;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "patients_information_id")
    private PatientsInformation patientsInformation;
    @JsonProperty("patients_information_id")
    public Integer getSpecialtiespatientsInformationId() {
        return patientsInformation != null ? patientsInformation.getId() : null;
    }
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @JsonProperty("doctor_id")
    public Integer getDoctorId() {
        return doctor != null ? doctor.getId() : null;
    }

}