package com.github.destructio.authorizator3000.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {
    private String username;
    private String password;
    private String email;
    private Set<String> rolesId;
}
