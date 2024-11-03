package com.rfrancos.crm.dto.login;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

}