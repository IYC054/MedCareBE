package fpt.aptech.pjs4.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cv_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CvImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "doctor_files_id")
    private Doctor doctorFile;
    @Lob
    @Column(name = "url_image")
    private String urlImage;


    @JsonProperty("doctor_files_id")
    public Integer getSpecialtiespatientsfileid() {
        return doctorFile != null ? doctorFile.getId() : null;
    }

}