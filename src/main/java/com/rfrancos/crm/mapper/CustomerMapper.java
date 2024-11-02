package com.rfrancos.crm.mapper;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.get.GetCustomerDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import com.rfrancos.crm.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    GetCustomerDto toGetCustomerDto(Customer customer);

    Customer createCustomerDtoToEntity(CreateCustomerDto createCustomerDto);

    Customer updateCustomerDtoToEntity(UpdateCustomerDto updateCustomerDto);
}
