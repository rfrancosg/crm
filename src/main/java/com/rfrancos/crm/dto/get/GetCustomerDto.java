package com.rfrancos.crm.dto.get;

import lombok.Data;

@Data
public class GetCustomerDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String photoUrl;
}
