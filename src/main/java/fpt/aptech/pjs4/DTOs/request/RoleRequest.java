package fpt.aptech.pjs4.DTOs.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Builder
public class RoleRequest {
    private String name;
    private String description;
    Set<String> permissions;
}
