package com.example.BloodBank.controller;

import com.example.BloodBank.adapters.CustomerMapper;
import com.example.BloodBank.adapters.UserMapper;
import com.example.BloodBank.dto.CustomerDTO;
import com.example.BloodBank.excpetions.EntityDoesntExistException;
import com.example.BloodBank.model.Customer;
import com.example.BloodBank.model.User;
import com.example.BloodBank.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/customer")
public class CustomerController {
    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    private CustomerMapper customerMapper;
    @Autowired
    public CustomerController(CustomerService customerService, ModelMapper modelMapper){
        this.customerService = customerService;
        this.modelMapper = modelMapper;
        this.customerMapper = new CustomerMapper(modelMapper);
    }
    @GetMapping()
    public List<Customer> getAllCustomers(){
        return customerService.getAll();
    }
    @PostMapping(
            value = "/register", consumes = "application/json",
            produces = "application/json"
    )
    public Customer registerCustomer(@RequestBody Customer newCustomer){
        return customerService.registerCustomer(newCustomer);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Update(@Valid @RequestBody CustomerDTO customerDTO){
        try {
            Customer customer = customerService.FindByUsername(customerDTO.getUsername());
            customerService.Update(updateCustomer(customer, customerDTO));
            return ResponseEntity.status(HttpStatus.OK).body(customerService.Read(customer.getId()));
        } catch (Exception e){
            if(e instanceof EntityDoesntExistException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private Customer updateCustomer(Customer customer ,CustomerDTO customerDTO){
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setDob(customerDTO.getDob());
        customer.setEmail(customerDTO.getEmail());
        customer.setGender(customerDTO.getGender());
        customer.setRole(customerDTO.getRole());
        return customer;
    }
}
