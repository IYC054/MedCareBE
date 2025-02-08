package fpt.aptech.pjs4.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fpt.aptech.pjs4.entities.PatientFile;
import jakarta.persistence.*;
import lombok.*;

@Data
public class FilesImageDTO {
    private Integer id;
    private String urlImage;
    private int patientsFiles;

}