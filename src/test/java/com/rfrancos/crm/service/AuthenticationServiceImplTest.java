package com.rfrancos.crm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rfrancos.crm.dto.login.LoginUserDto;
import com.rfrancos.crm.dto.login.RegisterUserDto;
import com.rfrancos.crm.entity.User;
import com.rfrancos.crm.exceptions.NotFoundException;
import com.rfrancos.crm.repository.UserRepository;
import com.rfrancos.crm.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void signup_ShouldEncodePasswordAndSaveUser() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName("Raul");
        registerUserDto.setSurname("Francos");
        registerUserDto.setEmail("rfrancos@example.com");
        registerUserDto.setPassword("rawPassword");

        User user = new User();
        user.setName("Raul");
        user.setSurname("Francos");
        user.setEmail("rfrancos@example.com");
        user.setPassword("encodedPassword");

        when(passwordEncoder.encode(registerUserDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authenticationService.signup(registerUserDto);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void authenticate_WhenCredentialsAreCorrect_ShouldReturnUser() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("rfrancos@example.com");
        loginUserDto.setPassword("password");

        User user = new User();
        user.setEmail("rfrancos@example.com");

        when(userRepository.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));

        User result = authenticationService.authenticate(loginUserDto);

        assertNotNull(result);
        assertEquals("rfrancos@example.com", result.getEmail());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(loginUserDto.getEmail());
    }

    @Test
    void authenticate_WhenUserNotFound_ShouldThrowNotFoundException() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("rfrancos@example.com");
        loginUserDto.setPassword("password");

        when(userRepository.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.empty());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));

        assertThrows(NotFoundException.class, () -> authenticationService.authenticate(loginUserDto));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(loginUserDto.getEmail());
    }

    @Test
    void authenticate_WhenAuthenticationFails_ShouldThrowAuthenticationException() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("rfrancos@example.com");
        loginUserDto.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Bad credentials") {});

        assertThrows(AuthenticationException.class, () -> authenticationService.authenticate(loginUserDto));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByEmail(loginUserDto.getEmail());
    }
}
