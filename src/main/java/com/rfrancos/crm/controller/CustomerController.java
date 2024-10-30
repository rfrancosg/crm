package com.rfrancos.crm.controller;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.get.GetCustomerDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import com.rfrancos.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<GetCustomerDto>> getAllCustomers(){
        LOGGER.info("get all customers ");
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCustomerDto> getCustomerById(@PathVariable Long id) {
        LOGGER.info("get customer with id: " + id);
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<GetCustomerDto> createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        LOGGER.info("Create customer with email: " + createCustomerDto.getEmail());
        return new ResponseEntity<>(customerService.createCustomer(createCustomerDto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<GetCustomerDto> updateCustomer(@RequestBody UpdateCustomerDto updateCustomerDto) {
        LOGGER.info("Update customer with id: " + updateCustomerDto.getId());
        return new ResponseEntity<>(customerService.updateCustomer(updateCustomerDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        LOGGER.info("Delete customer with id: " + id);
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.NO_CONTENT);
    }

}
