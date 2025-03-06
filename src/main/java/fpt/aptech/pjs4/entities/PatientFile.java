package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "vipappointment_id")
    private VipAppointment vipappointment;

    @OneToMany(mappedBy = "patientsFiles")
    private Set<FilesImage> filesImages = new LinkedHashSet<>();

    @ColumnDefault("sysutcdatetime()")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("sysutcdatetime()")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 100)
    @Nationalized
    @Column(name = "diagnosis", length = 100)
    private String diagnosis;

    @JsonProperty("vipappointment_id")
    public Integer getVipAppointmentId() {
        return vipappointment != null ? vipappointment.getId() : null;
    }
    @JsonProperty("appointment_id")
    public Integer getSpecialtiespatientsApp_id() {
        return appointment != null ? appointment.getId() : null;
    }
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