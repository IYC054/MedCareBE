package fpt.aptech.pjs4.DTOs.response;

import fpt.aptech.pjs4.entities.Permission;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    private String name;
    private String description;
    public PermissionResponse(Permission permission) {
        this.name = permission.getName();
        this.description = permission.getDescription();
    }


}
