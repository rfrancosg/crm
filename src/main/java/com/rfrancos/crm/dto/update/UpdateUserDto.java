package com.rfrancos.crm.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserDto {

    private Long id;
    private String name;
    private String surname;
    private String password;
    @JsonProperty("isAdmin")
    private boolean isAdmin;

}
