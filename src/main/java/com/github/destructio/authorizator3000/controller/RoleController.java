package com.github.destructio.authorizator3000.controller;

import com.github.destructio.authorizator3000.model.dto.RoleDto;
import com.github.destructio.authorizator3000.model.entity.Role;
import com.github.destructio.authorizator3000.service.RoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Role createRole(@RequestBody RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @GetMapping(path = "/{id}")
    public Role getRole(@PathVariable UUID id) {
        return roleService.getRole(id);
    }

    @PatchMapping(path = "/{id}")
    public void updateRole(@PathVariable UUID id, RoleDto roleDto) {
        roleService.updateRole(id, roleDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
