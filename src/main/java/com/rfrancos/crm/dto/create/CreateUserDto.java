package com.rfrancos.crm.dto.create;

import lombok.Data;

@Data
public class CreateUserDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
}
