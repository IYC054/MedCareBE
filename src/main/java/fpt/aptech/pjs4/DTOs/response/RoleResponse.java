package fpt.aptech.pjs4.DTOs.response;

import fpt.aptech.pjs4.entities.Role;
import fpt.aptech.pjs4.entities.Permission;  // Import cho Permission nếu cần
import lombok.*;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;  // Thêm permissions

    // Constructor chuyển đổi từ Role entity sang RoleResponse DTO
    public RoleResponse(Role role) {
        this.name = role.getName();
        this.description = role.getDescription();

        // Chuyển đổi danh sách Permission sang PermissionResponse
        if (role.getPermissions() != null) {
            this.permissions = role.getPermissions().stream()
                    .map(permission -> new PermissionResponse(permission)) // Giả sử PermissionResponse có constructor nhận Permission
                    .collect(Collectors.toSet());
        }
    }
}
