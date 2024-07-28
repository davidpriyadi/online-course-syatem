package com.mini.project.userservice.dto.mapper;

import com.mini.project.userservice.dto.RegisterDto;
import com.mini.project.userservice.dto.UsersDto;
import com.mini.project.userservice.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UsersDto toDto(Users users);
    Users toRegisterEntity(RegisterDto registerDto);
}
