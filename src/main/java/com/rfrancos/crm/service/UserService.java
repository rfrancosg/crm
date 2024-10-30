package com.rfrancos.crm.service;

import com.rfrancos.crm.dto.create.CreateUserDto;
import com.rfrancos.crm.dto.get.GetUserDto;
import com.rfrancos.crm.dto.update.UpdateUserDto;

import java.util.List;

public interface UserService {

    List<GetUserDto> getAllUsers();

    GetUserDto getUserById(Long id);

    GetUserDto createUser(CreateUserDto createUserDto);

    GetUserDto updateUser(UpdateUserDto updateUserDto);

    boolean deleteUser(Long id);
}
