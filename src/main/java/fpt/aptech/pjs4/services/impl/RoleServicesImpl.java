package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Role;
import fpt.aptech.pjs4.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServicesImpl {
    @Autowired
    private RoleRepository roleRepository;

    public Role getName(int id){
        return roleRepository.findById(id).get();
    }
}
