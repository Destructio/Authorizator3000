package com.github.destructio.authorizator3000.model.dto;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.util.Set;

@Data
@Builder
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String name;
    private URL pictureUrl;

    private Set<RoleDto> roles;
}
