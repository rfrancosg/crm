package com.rfrancos.crm.service.impl;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.get.GetCustomerDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import com.rfrancos.crm.entity.Customer;
import com.rfrancos.crm.exceptions.FileEmptyException;
import com.rfrancos.crm.exceptions.NotFoundException;
import com.rfrancos.crm.mapper.CustomerMapper;
import com.rfrancos.crm.repository.CustomerRepository;
import com.rfrancos.crm.service.CustomerService;
import com.rfrancos.crm.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final S3Service s3Service;

    @Override
    public List<GetCustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toGetCustomerDto).collect(Collectors.toList());
    }

    @Override
    public GetCustomerDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toGetCustomerDto)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
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

    @Override
    public GetCustomerDto uploadCustomerImage(MultipartFile file, Long customerId) throws IOException {
        if (file.isEmpty()) {
            throw new FileEmptyException("Please select a file to upload.");
        }
        String fileUrl = s3Service.uploadFile(file.getOriginalFilename(), file.getInputStream());
        Optional<Customer> mayCustomer = customerRepository.findById(customerId);
        if (!mayCustomer.isPresent()) {
            throw new NotFoundException("Customer not found with id: " + customerId);
        }
        mayCustomer.get().setPhotoUrl(fileUrl);
        return customerMapper.toGetCustomerDto(customerRepository.save(mayCustomer.get()));
    }
}
