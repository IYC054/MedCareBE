package fpt.aptech.projectapi.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", length = 100)
    private String email;
    @Size(min = 5, message = "Password must be at least 5 characters")
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