package fpt.aptech.pjs4.DTOs;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


@Data
public class AccountDTO {
    private Integer id;

    private String email;
    private String name;
    private String password;
    private String phone;
    private String gender;
    private LocalDate birthdate;
    //set mặc định khi create Account lun
    private List<String> role;
    private MultipartFile avatar;
}
