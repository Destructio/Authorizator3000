package com.github.destructio.authorizator3000.mapper;

import com.github.destructio.authorizator3000.provider.Provider;
import com.github.destructio.authorizator3000.exception.RoleNotFoundException;
import com.github.destructio.authorizator3000.api.dto.UserDto;
import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.model.User;
import com.github.destructio.authorizator3000.provider.ProviderUser;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = IGNORE)
public abstract class UserMapper {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "roles", qualifiedByName = "RolesToRolesDto")
    public abstract UserDto toDto(User user);

    @Mapping(target = "roles", qualifiedByName = "RolesDtoToRoles", nullValueCheckStrategy = ALWAYS)
    @Mapping(target = "password", qualifiedByName = "EncodePassword")
    public abstract User toUser(UserDto userDto);

    @Mapping(target = "pictureUrl", source = "imageUrl")
    @Mapping(target = "provider", source = "authProvider")
    @Mapping(target = "roles", source = "attributes", qualifiedByName = "GetUserRole")
    public abstract User toUser(ProviderUser ProviderUser);

    @Mapping(target = "username", source = "preferredUsername")
    @Mapping(target = "pictureUrl", source = "picture")
    @Mapping(target = "provider", source = "userInfo", qualifiedByName = "GetOidcProvider")
    @Mapping(target = "roles", source = "userInfo", qualifiedByName = "GetUserRole")
    public abstract User toUser(OidcUser oidcUser);

    @Mapping(target = "roles", qualifiedByName = "RolesDtoToRoles", nullValueCheckStrategy = ALWAYS)
    @Mapping(target = "password", qualifiedByName = "EncodePassword")
    public abstract void updateUser(UserDto userDto, @MappingTarget User user);

    @Named("RolesToRolesDto")
    public Set<UUID> rolesToRolesId(Set<Role> roles) {
        if (roles == null)
            return null;
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }

    @Named("RolesDtoToRoles")
    public Set<Role> rolesDtoToRoles(Set<UUID> roles) {
        if (roles == null)
            return null;
        return roles.stream()
                .map(uuid -> roleRepository.findById(uuid)
                        .orElseThrow(() -> new RoleNotFoundException(uuid)))
                .collect(Collectors.toSet());
    }

    @Named("EncodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("GetOidcProvider")
    public Provider getOidcProvider(OidcUserInfo userInfo) {
        return Provider.OIDC;
    }

    @Named("GetUserRole")
    public Set<Role> getUserRole(Object o) {
        String userRoleName = "USER";

        Role role = roleRepository.findByName(userRoleName)
                .orElseThrow(() -> new RoleNotFoundException(userRoleName));
        return Set.of(role);
    }
}

