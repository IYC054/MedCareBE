package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.request.RoleRequest;
import fpt.aptech.pjs4.DTOs.response.PermissionResponse;
import fpt.aptech.pjs4.DTOs.response.RoleResponse;
import fpt.aptech.pjs4.entities.Role;
import fpt.aptech.pjs4.repositories.PermissionRepository;
import fpt.aptech.pjs4.repositories.RoleRepository;
import fpt.aptech.pjs4.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl  implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public RoleResponse createRole(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());

      // lưu ý chuyển đổi thanh mảng
        Set<PermissionResponse> permissionResponses = role.getPermissions().stream()
                .map(permission -> new PermissionResponse(permission)) // Chuyển từng Permission thành PermissionResponse
                .collect(Collectors.toSet());

        roleResponse.setPermissions(permissionResponses); // Gán Set<PermissionResponse> vào RoleResponse

        return roleResponse;
    }


    @Override
    public List<RoleResponse> getAllRole() {
        var roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> new RoleResponse(role))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRole(String role) {
        roleRepository.deleteById(role);

    }
}
