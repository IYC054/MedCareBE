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
@Table(name = "Specialties")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Nationalized
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 200)
    @Nationalized
    @Column(name = "description", length = 200)
    private String description;
    @Size(max = 200)

    @Nationalized
    @Column(name = "image", length = 200)
    private String image;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @JsonProperty("doctor_id")
    public Integer getDoctorId() {
        return doctor != null ? doctor.getId() : null;
    }


}