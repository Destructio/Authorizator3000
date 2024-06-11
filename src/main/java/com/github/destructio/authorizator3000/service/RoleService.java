package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.exception.RoleNotFoundException;
import com.github.destructio.authorizator3000.mapper.RoleMapper;
import com.github.destructio.authorizator3000.api.dto.RoleDto;
import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role createRole(RoleDto roleDto) {
        Role role = roleMapper.toRole(roleDto);
        return roleRepository.save(role);
    }

    public Role getRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    public Role updateRole(UUID id, RoleDto roleDto) {
        Role role = getRole(id);
        roleMapper.update(roleDto, role);
        return roleRepository.save(role);
    }

    public void deleteRole(UUID id) {
        Role role = getRole(id);
        roleRepository.delete(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRole(String roleName) {
        return roleRepository.findByName(roleName);
    }
}