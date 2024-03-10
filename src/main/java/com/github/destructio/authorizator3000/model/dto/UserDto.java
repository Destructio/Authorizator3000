package com.github.destructio.authorizator3000.model.dto;

import lombok.Builder;

import java.net.URI;
import java.util.Set;

@Builder
public record UserDto(
        String username,
        String password,
        String email,
        String name,
        URI pictureUrl,
        Set<RoleDto> roles) {
}
