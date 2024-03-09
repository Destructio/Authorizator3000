package com.github.destructio.authorizator3000.mapper;

import com.github.destructio.authorizator3000.model.dto.RoleDto;
import com.github.destructio.authorizator3000.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RoleMapper {
    RoleDto toDto(Role role);

    Role toRole(RoleDto roleDto);

    void update(UUID id, @MappingTarget RoleDto roleDto);

}
