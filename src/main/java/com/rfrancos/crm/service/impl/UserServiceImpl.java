package com.rfrancos.crm.service.impl;

import com.rfrancos.crm.dto.create.CreateUserDto;
import com.rfrancos.crm.dto.get.GetUserDto;
import com.rfrancos.crm.dto.update.UpdateUserDto;
import com.rfrancos.crm.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<GetUserDto> getAllUsers() {
        return null;
    }

    @Override
    public GetUserDto getUserById(Long id) {
        return null;
    }

    @Override
    public GetUserDto createUser(CreateUserDto createUserDto) {
        return null;
    }

    @Override
    public GetUserDto updateUser(UpdateUserDto updateUserDto) {
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }
}
