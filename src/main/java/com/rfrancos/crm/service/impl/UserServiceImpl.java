package com.rfrancos.crm.service.impl;

import com.rfrancos.crm.dto.create.CreateUserDto;
import com.rfrancos.crm.dto.get.GetUserDto;
import com.rfrancos.crm.dto.update.UpdateUserDto;
import com.rfrancos.crm.exceptions.CustomException;
import com.rfrancos.crm.mapper.UserMapper;
import com.rfrancos.crm.repository.UserRepository;
import com.rfrancos.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toGetUserDto).collect(Collectors.toList());
    }

    @Override
    public GetUserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toGetUserDto)
                .orElseThrow(() -> new CustomException("User not found for id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public GetUserDto createUser(CreateUserDto createUserDto) {
        try {
            return userMapper.toGetUserDto(userRepository.save(userMapper.createUserDtoToEntity(createUserDto)));
        }
        catch (DataIntegrityViolationException exception) {
            throw new CustomException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GetUserDto updateUser(UpdateUserDto updateUserDto) {
        try {
            return userMapper.toGetUserDto(userRepository.save(userMapper.updateUserDtoToEntity(updateUserDto)));
        }
        catch (DataIntegrityViolationException exception) {
            throw new CustomException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
