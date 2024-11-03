package com.rfrancos.crm.service;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.get.GetCustomerDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CustomerService {


    List<GetCustomerDto> getAllCustomers();

    GetCustomerDto getCustomerById(Long id);

    GetCustomerDto createCustomer(CreateCustomerDto createCustomerDto);

    GetCustomerDto updateCustomer(UpdateCustomerDto updateCustomerDto);

    boolean deleteCustomer(Long id);

    GetCustomerDto uploadCustomerImage(MultipartFile multipartFile, Long customerId) throws IOException;
}
