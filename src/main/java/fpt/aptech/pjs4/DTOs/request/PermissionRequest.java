package fpt.aptech.pjs4.DTOs.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PermissionRequest {
    private String name;
    private String description;
}
