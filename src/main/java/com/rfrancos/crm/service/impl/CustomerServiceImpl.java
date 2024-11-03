package com.rfrancos.crm.service.impl;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.get.GetCustomerDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import com.rfrancos.crm.entity.Customer;
import com.rfrancos.crm.entity.User;
import com.rfrancos.crm.exceptions.FileEmptyException;
import com.rfrancos.crm.exceptions.NotFoundException;
import com.rfrancos.crm.mapper.CustomerMapper;
import com.rfrancos.crm.repository.CustomerRepository;
import com.rfrancos.crm.service.CustomerService;
import com.rfrancos.crm.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Customer customer = customerMapper.createCustomerDtoToEntity(createCustomerDto);
        customer.setCreatedBy(userDetails);
        return customerMapper.toGetCustomerDto(customerRepository.save(customer));
    }

    @Override
    public GetCustomerDto updateCustomer(UpdateCustomerDto updateCustomerDto) {
        Optional<Customer> mayCustomer = customerRepository.findById(updateCustomerDto.getId());
        if (!mayCustomer.isPresent()) {
            throw new NotFoundException("Customer not found with id: " + updateCustomerDto.getId());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        mayCustomer.get().setName(updateCustomerDto.getName());
        mayCustomer.get().setSurname(updateCustomerDto.getSurname());
        mayCustomer.get().setUpdatedBy(userDetails);
        return customerMapper.toGetCustomerDto(customerRepository.save(mayCustomer.get()));
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        mayCustomer.get().setUpdatedBy(userDetails);
        mayCustomer.get().setPhotoUrl(fileUrl);
        return customerMapper.toGetCustomerDto(customerRepository.save(mayCustomer.get()));
    }
}
