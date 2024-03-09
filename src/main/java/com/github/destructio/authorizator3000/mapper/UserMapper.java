package com.github.destructio.authorizator3000.mapper;

import com.github.destructio.authorizator3000.exceptions.RoleNotFoundException;
import com.github.destructio.authorizator3000.model.dto.RoleDto;
import com.github.destructio.authorizator3000.model.dto.UserDto;
import com.github.destructio.authorizator3000.model.entity.Role;
import com.github.destructio.authorizator3000.model.entity.User;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class UserMapper {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "roles", qualifiedByName = "RolesToRolesDto")
    public abstract UserDto toDto(User user);

    @Mapping(target = "roles", qualifiedByName = "RolesDtoToRoles")
    @Mapping(target = "password", qualifiedByName = "EncodePassword")
    public abstract User toUser(UserDto userDto);

    @Mapping(target = "roles", qualifiedByName = "RolesDtoToRoles")
    @Mapping(target = "password", qualifiedByName = "EncodePassword")
    public abstract void updateUser(UserDto userDto, @MappingTarget User user);

    @Named("RolesToRolesDto")
    public Set<RoleDto> rolesToRolesId(Set<Role> roles) {
        return roles.stream()
                .map(role -> RoleDto.builder()
                        .name(role.getName())
                        .build())
                .collect(Collectors.toSet());
    }

    @Named("RolesDtoToRoles")
    public Set<Role> rolesDtoToRoles(Set<RoleDto> roles) {
        return roles.stream()
                .map(roleDto -> roleRepository.findByName(roleDto.getName())
                        .orElseThrow(() -> new RoleNotFoundException(roleDto.getName())))
                .collect(Collectors.toSet());
    }

    @Named("EncodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}

