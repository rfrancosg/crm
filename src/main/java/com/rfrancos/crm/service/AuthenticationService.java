package com.rfrancos.crm.service;

import com.rfrancos.crm.dto.LoginUserDto;
import com.rfrancos.crm.dto.RegisterUserDto;
import com.rfrancos.crm.entity.User;

public interface AuthenticationService {

    User signup(RegisterUserDto input);

    User authenticate(LoginUserDto input);
}
