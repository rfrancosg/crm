package com.rfrancos.crm.controller;

import com.rfrancos.crm.dto.create.CreateCustomerDto;
import com.rfrancos.crm.dto.create.CreateUserDto;
import com.rfrancos.crm.dto.get.GetUserDto;
import com.rfrancos.crm.dto.update.UpdateCustomerDto;
import com.rfrancos.crm.dto.update.UpdateUserDto;
import com.rfrancos.crm.service.UserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<GetUserDto>> getAllUsers(){
        LOGGER.info("get all users ");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Long id) {
        LOGGER.info("get user with id: " + id);
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<GetUserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        LOGGER.info("Create user with email: " + createUserDto.getEmail());
        return new ResponseEntity<>(userService.createUser(createUserDto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<GetUserDto> updateUser(@RequestBody UpdateUserDto updateUserDto) {
        LOGGER.info("Update user with email: " + updateUserDto.getEmail());
        return new ResponseEntity<>(userService.updateUser(updateUserDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        LOGGER.info("Delete user with id: " + id);
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.NO_CONTENT);
    }


}
