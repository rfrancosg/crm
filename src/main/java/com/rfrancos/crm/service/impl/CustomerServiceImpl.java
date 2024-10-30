package com.rfrancos.crm.service.impl;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.get.GetCustomerDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import com.rfrancos.crm.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public List<GetCustomerDto> getAllCustomers() {
        return null;
    }

    @Override
    public GetCustomerDto getCustomerById(Long id) {
        return null;
    }

    @Override
    public GetCustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        return null;
    }

    @Override
    public GetCustomerDto updateCustomer(UpdateCustomerDto updateCustomerDto) {
        return null;
    }

    @Override
    public boolean deleteCustomer(Long id) {
        return false;
    }
}
