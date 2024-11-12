package fpt.aptech.pjs4.DTOs;

import lombok.*;

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
