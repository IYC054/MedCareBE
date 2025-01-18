package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.request.PermissionRequest;
import fpt.aptech.pjs4.DTOs.response.PermissionResponse;
import fpt.aptech.pjs4.entities.Permission;
import fpt.aptech.pjs4.repositories.PermissionRepository;
import fpt.aptech.pjs4.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl  implements PermissionService {
    @Autowired
     private PermissionRepository permissionRepository;
    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        permissionRepository.save(permission);
        PermissionResponse response = new PermissionResponse();
        response.setName(permission.getName());
        response.setDescription(permission.getDescription());
        return response;

    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permission -> new PermissionResponse(permission))  // Truyền đối tượng permission vào constructor
                .collect(Collectors.toList());
    }



    @Override
    public void deletePermission(String permission) {
        permissionRepository.deleteById(permission);


    }
}
