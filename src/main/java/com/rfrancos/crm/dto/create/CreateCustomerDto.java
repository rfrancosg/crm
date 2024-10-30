package com.rfrancos.crm.dto.create;

import lombok.Data;

@Data
public class CreateCustomerDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String photoUrl;
}
