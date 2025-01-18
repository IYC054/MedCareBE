package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.APIResponse;
import fpt.aptech.pjs4.DTOs.request.PermissionRequest;
import fpt.aptech.pjs4.DTOs.response.PermissionResponse;
import fpt.aptech.pjs4.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    APIResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        APIResponse<PermissionResponse> response = new APIResponse<>();
         response.setResult(permissionService.createPermission(request));
         return response;
    }
    @GetMapping
    APIResponse<List<PermissionResponse>> getPermissions() {
        APIResponse<List<PermissionResponse>> response = new APIResponse<>();
        response.setResult(permissionService.getAllPermissions());
        return response;
    }
    @DeleteMapping("/{permission}")
    APIResponse<Void> deletePermission(@PathVariable("permission") String permission) {
        permissionService.deletePermission(permission);
        APIResponse<Void> response = new APIResponse<>();
        return response;
    }
}
