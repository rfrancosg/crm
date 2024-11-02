package com.rfrancos.crm.mapper;

import com.rfrancos.crm.dto.create.CreateUserDto;
import com.rfrancos.crm.dto.get.GetUserDto;
import com.rfrancos.crm.dto.update.UpdateUserDto;
import com.rfrancos.crm.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    GetUserDto toGetUserDto(User user);

    User createUserDtoToEntity(CreateUserDto createUserDto);

    User updateUserDtoToEntity(UpdateUserDto updateUserDto);
}
