package com.rfrancos.crm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.get.GetCustomerDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import com.rfrancos.crm.entity.Customer;
import com.rfrancos.crm.entity.User;
import com.rfrancos.crm.exceptions.FileEmptyException;
import com.rfrancos.crm.exceptions.NotFoundException;
import com.rfrancos.crm.mapper.CustomerMapper;
import com.rfrancos.crm.repository.CustomerRepository;
import com.rfrancos.crm.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private User user;

    @Mock
    private MultipartFile file;

    @Test
    void getAllCustomers_ShouldReturnCustomerDtoList() {
        Customer customer = new Customer();
        GetCustomerDto customerDto = new GetCustomerDto();
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
        when(customerMapper.toGetCustomerDto(customer)).thenReturn(customerDto);

        List<GetCustomerDto> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        verify(customerRepository, times(1)).findAll();
        verify(customerMapper, times(1)).toGetCustomerDto(customer);
    }

    @Test
    void getCustomerById_WhenCustomerExists_ShouldReturnCustomerDto() {
        Long customerId = 1L;
        Customer customer = new Customer();
        GetCustomerDto customerDto = new GetCustomerDto();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.toGetCustomerDto(customer)).thenReturn(customerDto);

        GetCustomerDto result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerMapper, times(1)).toGetCustomerDto(customer);
    }

    @Test
    void getCustomerById_WhenCustomerDoesNotExist_ShouldThrowNotFoundException() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.getCustomerById(customerId));
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void createCustomer_ShouldReturnCreatedCustomerDto() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto();
        Customer customer = new Customer();
        GetCustomerDto customerDto = new GetCustomerDto();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        when(customerMapper.createCustomerDtoToEntity(createCustomerDto)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toGetCustomerDto(customer)).thenReturn(customerDto);

        GetCustomerDto result = customerService.createCustomer(createCustomerDto);

        assertNotNull(result);
        verify(customerRepository, times(1)).save(customer);
        verify(customerMapper, times(1)).toGetCustomerDto(customer);
    }

    @Test
    void updateCustomer_WhenCustomerExists_ShouldReturnUpdatedCustomerDto() {
        UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto();
        updateCustomerDto.setId(1L);
        updateCustomerDto.setName("Updated Name");
        updateCustomerDto.setSurname("Updated Surname");
        Customer customer = new Customer();
        GetCustomerDto customerDto = new GetCustomerDto();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        when(customerRepository.findById(updateCustomerDto.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toGetCustomerDto(customer)).thenReturn(customerDto);

        GetCustomerDto result = customerService.updateCustomer(updateCustomerDto);

        assertNotNull(result);
        verify(customerRepository, times(1)).save(customer);
        verify(customerMapper, times(1)).toGetCustomerDto(customer);
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer() {
        boolean result = customerService.deleteCustomer(1L);

        assertTrue(result);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void uploadCustomerImage_WhenFileIsEmpty_ShouldThrowFileEmptyException() {
        when(file.isEmpty()).thenReturn(true);

        assertThrows(FileEmptyException.class, () -> customerService.uploadCustomerImage(file, 1L));
        verify(s3Service, never()).uploadFile(anyString(), any(InputStream.class));
    }
}
