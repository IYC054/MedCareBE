package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vip_appointments")
public class VipAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToMany(mappedBy = "vipappointment")
    private Set<Payment> payments = new LinkedHashSet<>();

    @JsonProperty("patient_id")
    public Integer getPatientId() {
        return doctor != null ? patient.getId() : null;
    }
    @ManyToOne(targetEntity = Doctor.class)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
//    @JsonProperty("doctor_id")
//    public Integer getDoctorId() {
//        return doctor != null ? doctor.getId() : null;
//    }
    @NotNull
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Size(max = 20)
    @Nationalized
    @Column(name = "status", length = 20)
    private String status;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @ManyToOne(targetEntity = PatientsInformation.class)
//    @JsonIgnore
    @JoinColumn(name = "patientprofile_id", nullable = false)
    private PatientsInformation patientprofile;
//    @JsonProperty("patientprofile_id")
//    public Integer getPatientProfileId() {
//        return patientprofile != null ? patientprofile.getId() : null;
//    }
    @Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "BHYT")
    private Boolean bhyt;
}