package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "cccd")
    private String cccd;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "doctorFile")
    private Set<CvImage> filesImages = new LinkedHashSet<>();

    // Liên kết với tài khoản người dùng
    @OneToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "Doctors_Specialties",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private List<Specialty> specialties;

    @Column(name = "vip")
    private Boolean vip;


}