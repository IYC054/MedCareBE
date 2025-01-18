package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.DTOs.request.PermissionRequest;
import fpt.aptech.pjs4.DTOs.response.PermissionResponse;

import java.util.List;


public interface PermissionService {
  PermissionResponse createPermission(PermissionRequest request);
  List<PermissionResponse> getAllPermissions();
  void deletePermission(String permission);

}
