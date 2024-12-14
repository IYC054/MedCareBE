package fpt.aptech.pjs4.DTOs;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Data
public class AccountDTO {
    private Integer id;

    private String email;
    private String name;
    private String password;
    private String phone;
    private String gender;
    private LocalDate birthdate;
    private String role;
    private MultipartFile avatar;

}
