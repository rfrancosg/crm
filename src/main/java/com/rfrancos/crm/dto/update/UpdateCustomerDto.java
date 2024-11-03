package com.rfrancos.crm.dto.update;

import lombok.Data;

@Data
public class UpdateCustomerDto {

    private Long id;
    private String name;
    private String surname;
    private String photoUrl;

}
