package com.rfrancos.crm.service.impl;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.get.GetCustomerDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import com.rfrancos.crm.exceptions.NotFoundException;
import com.rfrancos.crm.mapper.CustomerMapper;
import com.rfrancos.crm.repository.CustomerRepository;
import com.rfrancos.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<GetCustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toGetCustomerDto).collect(Collectors.toList());
    }

    @Override
    public GetCustomerDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toGetCustomerDto)
                .orElseThrow(() -> new NotFoundException("Customer not found for id: " + id));
    }

    @Override
    public GetCustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        return customerMapper.toGetCustomerDto(customerRepository.save(customerMapper.createCustomerDtoToEntity(createCustomerDto)));
    }

    @Override
    public GetCustomerDto updateCustomer(UpdateCustomerDto updateCustomerDto) {
        return customerMapper.toGetCustomerDto(customerRepository.save(customerMapper.updateCustomerDtoToEntity(updateCustomerDto)));
    }

    @Override
    public boolean deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return true;
    }
}
