package com.github.destructio.authorizator3000.api.dto;

import jakarta.validation.constraints.Size;

public record RoleDto(
        @Size(min = 1, max = 50) String name) {
}
