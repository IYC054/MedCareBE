package fpt.aptech.pjs4.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilesImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "url_image", length = 50)
    private String urlImage;
    @ManyToOne(targetEntity = PatientFile.class)
    @JoinColumn(name = "patients_files_id")
    private PatientFile patientsFiles;

}