package fpt.aptech.pjs4.services;


import fpt.aptech.pjs4.DTOs.request.RoleRequest;
import fpt.aptech.pjs4.DTOs.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
    List<RoleResponse> getAllRole();
    void deleteRole(String role);
}
