package com.github.destructio.authorizator3000.controller.api;

import com.github.destructio.authorizator3000.controller.dto.RoleDto;
import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Role createRole(@RequestBody RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Role getRole(@PathVariable UUID id) {
        return roleService.getRole(id);
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRole(@PathVariable UUID id, RoleDto roleDto) {
        roleService.updateRole(id, roleDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
