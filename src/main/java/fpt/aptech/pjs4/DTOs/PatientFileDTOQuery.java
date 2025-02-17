package fpt.aptech.pjs4.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatientFileDTOQuery {
    private Integer id;
    private String description;
    private String urlImage;

    public PatientFileDTOQuery(Integer id, String description, String urlImage) {
        this.id = id;
        this.description = description;
        this.urlImage = urlImage;
    }

    // Getter và Setter
}


