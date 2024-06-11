package com.github.destructio.authorizator3000.api.controller;

import com.github.destructio.authorizator3000.api.dto.RoleDto;
import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/roles", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Role createRole(@RequestBody @Valid RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @GetMapping(path = "/{id}")
    public Role getRole(@PathVariable UUID id) {
        return roleService.getRole(id);
    }

    @PatchMapping(path = "/{id}")
    public Role updateRole(@PathVariable UUID id, @RequestBody @Valid RoleDto roleDto) {
        return roleService.updateRole(id, roleDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
