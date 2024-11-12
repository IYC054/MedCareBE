package fpt.aptech.pjs4.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class APIResponse<T> {
    private int code = 200;
    private String message;
    private T result;

}