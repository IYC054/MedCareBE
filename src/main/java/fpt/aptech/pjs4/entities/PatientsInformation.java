package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "patients_information")
public class PatientsInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Nationalized
    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Size(max = 20)
    @Nationalized
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 10)
    @Nationalized
    @Column(name = "gender", length = 10)
    private String gender;



    @Size(max = 20)
    @Nationalized
    @Column(name = "nation", length = 20)
    private String nation;


    @Size(max = 100)
    @Nationalized
    @Column(name = "address", length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    @Size(max = 20)
    @Nationalized
    @Column(name = "identification_card", length = 20)
    private String identificationCard;

    @JsonProperty("account_id")
    public Integer getAppointmentId() {
        return account != null ? account.getId() : null;
    }
}