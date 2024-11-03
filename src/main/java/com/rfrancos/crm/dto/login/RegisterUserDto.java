package com.rfrancos.crm.dto.login;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String email;
    
    private String password;
    
    private String name;

    private String surname;

}