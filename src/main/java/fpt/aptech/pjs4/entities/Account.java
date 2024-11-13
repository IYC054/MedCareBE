package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", length = 100)
    private String email;
    @Size(message = "Password must be at least 5 characters")
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "role", length = 20)
    private String role;

}
