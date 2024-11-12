package fpt.aptech.pjs4.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authentication {
    private String token;
    boolean authenticated;
}