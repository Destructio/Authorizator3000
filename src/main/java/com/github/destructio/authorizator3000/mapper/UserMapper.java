package com.github.destructio.authorizator3000.mapper;

import com.github.destructio.authorizator3000.controller.dto.UserDto;
import com.github.destructio.authorizator3000.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
//    @Mapping(target = "passwordHash", source = "dto.password")
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
