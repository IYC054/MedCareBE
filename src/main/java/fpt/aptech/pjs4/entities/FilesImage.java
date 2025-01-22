package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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


    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "patients_files_id")
    private PatientFile patientsFiles;

    @Lob
    @Column(name = "url_image")
    private String urlImage;


    @JsonProperty("patients_files_id")
    public Integer getSpecialtiespatientsfileid() {
        return patientsFiles != null ? patientsFiles.getId() : null;
    }

}