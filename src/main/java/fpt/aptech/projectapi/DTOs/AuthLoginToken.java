package fpt.aptech.projectapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthLoginToken {
    private int userId;
    private String email;
    private String token;
    private String role;
    private String gender;
}
