package com.rfrancos.crm.service;

import com.rfrancos.crm.dto.login.LoginUserDto;
import com.rfrancos.crm.dto.login.RegisterUserDto;
import com.rfrancos.crm.entity.User;

public interface AuthenticationService {

    User signup(RegisterUserDto input);

    User authenticate(LoginUserDto input);
}
