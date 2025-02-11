package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "worktime")
    private Instant worktime;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "description", length = 250)
    private String description;

    @Size(max = 250)
    @Nationalized
    @Column(name = "url_image", length = 250)
    private String urlImage;

}