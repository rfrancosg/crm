package com.rfrancos.crm.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateUserDto {

    private String name;
    private String surname;
    private String email;
    private String password;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
}
