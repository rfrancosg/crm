package com.rfrancos.crm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rfrancos.crm.dto.create.CreateUserDto;
import com.rfrancos.crm.dto.get.GetUserDto;
import com.rfrancos.crm.dto.update.UpdateUserDto;
import com.rfrancos.crm.entity.User;
import com.rfrancos.crm.exceptions.NotFoundException;
import com.rfrancos.crm.mapper.UserMapper;
import com.rfrancos.crm.repository.UserRepository;
import com.rfrancos.crm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAllUsers_ShouldReturnUserDtoList() {
        User user = new User();
        GetUserDto userDto = new GetUserDto();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userMapper.toGetUserDto(user)).thenReturn(userDto);

        List<GetUserDto> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toGetUserDto(user);
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUserDto() {
        Long userId = 1L;
        User user = new User();
        GetUserDto userDto = new GetUserDto();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toGetUserDto(user)).thenReturn(userDto);

        GetUserDto result = userService.getUserById(userId);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toGetUserDto(user);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldThrowNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void createUser_ShouldEncodePasswordAndReturnUserDto() {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setPassword("rawPassword");

        User user = new User();
        GetUserDto userDto = new GetUserDto();

        when(passwordEncoder.encode(createUserDto.getPassword())).thenReturn("encodedPassword");
        when(userMapper.createUserDtoToEntity(createUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toGetUserDto(user)).thenReturn(userDto);

        GetUserDto result = userService.createUser(createUserDto);

        assertNotNull(result);
        assertEquals("encodedPassword", createUserDto.getPassword());
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toGetUserDto(user);
    }

    @Test
    void updateUser_WhenPasswordIsProvided_ShouldEncodePasswordAndReturnUpdatedUserDto() {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setPassword("newPassword");

        User user = new User();
        GetUserDto userDto = new GetUserDto();

        when(passwordEncoder.encode(updateUserDto.getPassword())).thenReturn("encodedNewPassword");
        when(userMapper.updateUserDtoToEntity(updateUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toGetUserDto(user)).thenReturn(userDto);

        GetUserDto result = userService.updateUser(updateUserDto);

        assertNotNull(result);
        assertEquals("encodedNewPassword", updateUserDto.getPassword());
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toGetUserDto(user);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
