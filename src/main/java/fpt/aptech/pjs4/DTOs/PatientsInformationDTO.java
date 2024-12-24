package fpt.aptech.pjs4.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientsInformationDTO {
    private String fullname;
    private String birthdate;
    private String phone;
    private String gender;
    private String codeBhyt;
    private String nation;
    private String identityCard;
    private String address;
    private Integer accountid;
}
