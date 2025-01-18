package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.APIResponse;
import fpt.aptech.pjs4.DTOs.request.RoleRequest;
import fpt.aptech.pjs4.DTOs.response.RoleResponse;
import fpt.aptech.pjs4.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    APIResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        APIResponse<RoleResponse> response = new APIResponse<>();
         response.setResult(roleService.createRole(request));
         return response;
    }
    @GetMapping
    APIResponse<List<RoleResponse>> getAllRoles() {
        APIResponse<List<RoleResponse>> response = new APIResponse<>();
        response.setResult(roleService.getAllRole());
        return response;
    }
    @DeleteMapping("/{role}")
    APIResponse<Void> deleteRole(@PathVariable("role") String role) {
        roleService.deleteRole(role);
        APIResponse<Void> response = new APIResponse<>();
        return response;
    }
}
