package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.controller.dto.RoleDto;
import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(RoleDto roleDto) {
        return null;
    }

    @Transactional
    public Role createRole(UUID id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        roleRepository.save(role);
        return role;
    }

    public Role getRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role is not found! id: " + id));
    }

    public void updateRole(UUID id, RoleDto roleDto) {

    }

    public void deleteRole(UUID id) {
    }

    public List<Role> getAllRoles() {
        return null;
    }

    public Role createUserRole() {
        Role role = new Role();
        role.setName("USER");
        roleRepository.saveAndFlush(role);
        return role;
    }

    public Role createAdminRole() {
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.saveAndFlush(role);
        return role;
    }
}
