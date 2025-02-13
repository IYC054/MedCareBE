package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "news")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "images", length = 250)
    private String images;

    @Column(name = "\"date\"")
    private LocalDate date;

}
