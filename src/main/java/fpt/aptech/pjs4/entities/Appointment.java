package fpt.aptech.pjs4.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(targetEntity = Patient.class)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(targetEntity = Doctor.class)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @ManyToOne(targetEntity = Doctorworking.class)
    @JoinColumn(name = "worktime_id")
    private Doctorworking worktime;

    @ManyToOne(targetEntity = PatientsInformation.class)
    @JoinColumn(name = "patientprofile_id")
    private PatientsInformation patientprofile;

    @OneToMany(mappedBy = "appointment")
    private Set<PatientFile> patientFiles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "appointment")
    private Set<Payment> payments = new LinkedHashSet<>();

    @Column(name = "BHYT")
    private Boolean bhyt;

}
