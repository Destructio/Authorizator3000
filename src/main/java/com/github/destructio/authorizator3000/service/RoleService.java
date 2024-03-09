package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.exceptions.RoleNotFoundException;
import com.github.destructio.authorizator3000.mapper.RoleMapper;
import com.github.destructio.authorizator3000.model.dto.RoleDto;
import com.github.destructio.authorizator3000.model.entity.Role;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public Role createRole(RoleDto roleDto) {
        Role role = roleMapper.toRole(roleDto);
        return roleRepository.save(role);
    }

    public Role getRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    public void updateRole(UUID id, RoleDto roleDto) {
        Role role = getRole(id);
        roleMapper.update(id, roleDto);
        roleRepository.save(role);
    }

    public void deleteRole(UUID id) {
        Role role = getRole(id);
        roleRepository.delete(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}