package com.github.destructio.authorizator3000.mapper;

import com.github.destructio.authorizator3000.api.dto.RoleDto;
import com.github.destructio.authorizator3000.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RoleMapper {
    RoleDto toDto(Role role);

    Role toRole(RoleDto roleDto);

    void update(RoleDto roleDto, @MappingTarget Role role);

}
