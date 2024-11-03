package com.rfrancos.crm.service.impl;

import com.rfrancos.crm.dto.create.CreateUserDto;
import com.rfrancos.crm.dto.get.GetUserDto;
import com.rfrancos.crm.dto.update.UpdateUserDto;
import com.rfrancos.crm.exceptions.NotFoundException;
import com.rfrancos.crm.mapper.UserMapper;
import com.rfrancos.crm.repository.UserRepository;
import com.rfrancos.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toGetUserDto).collect(Collectors.toList());
    }

    @Override
    public GetUserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toGetUserDto)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Override
    public GetUserDto createUser(CreateUserDto createUserDto) {
        if (createUserDto.getPassword() != null) {
            createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        }
        return userMapper.toGetUserDto(userRepository.save(userMapper.createUserDtoToEntity(createUserDto)));
    }

    @Override
    public GetUserDto updateUser(UpdateUserDto updateUserDto) {
        if (updateUserDto.getPassword() != null) {
            updateUserDto.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }
        return userMapper.toGetUserDto(userRepository.save(userMapper.updateUserDtoToEntity(updateUserDto)));
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
