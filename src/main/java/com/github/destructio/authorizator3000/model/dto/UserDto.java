package com.github.destructio.authorizator3000.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import java.net.URI;
import java.util.Set;

@Builder
public record UserDto(
        @Size(min = 1, max = 20) String username,
        @Size(min = 5, max = 20) String password,
        @Email String email,
        @Size(min = 1, max = 50) String name,
        @URL URI pictureUrl,
        Set<@Valid RoleDto> roles) {
}
