package com.rfrancos.crm.service.impl;

import com.rfrancos.crm.dto.LoginUserDto;
import com.rfrancos.crm.dto.RegisterUserDto;
import com.rfrancos.crm.entity.User;
import com.rfrancos.crm.exceptions.NotFoundException;
import com.rfrancos.crm.repository.UserRepository;
import com.rfrancos.crm.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    @Override
    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setName(input.getName());
        user.setSurname(input.getSurname());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new NotFoundException("Customer not found with email: " + input.getEmail()));
    }
}