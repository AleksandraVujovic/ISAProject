package com.example.BloodBank.controller;


import com.example.BloodBank.dto.userDTOs.AdminDTO;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.Admin;
import com.example.BloodBank.dto.userDTOs.RegistrationAdminDTO;
import com.example.BloodBank.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/admin")
public class AdminController {

    private final AdminService adminService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(AdminService adminService, ModelMapper modelMapper) {

        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/{id}")
    public Admin Read(@PathVariable("id") long id) {
        try {
            return adminService.Read(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e instanceof EntityDoesntExistException) {
                new EntityDoesntExistException();
            }
            throw new RuntimeException(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Update(@RequestBody AdminDTO adminDTO){
       // System.out.println("pogodili smo update " + adminDTO);
        try{
            Admin admin = modelMapper.map(adminDTO, Admin.class);
             adminService.Update(admin);
            System.out.println("Uspeli smo!");
             return ResponseEntity.status(HttpStatus.OK).body(adminService.Read(admin.getId()));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> registerAdmin(@Valid @RequestBody RegistrationAdminDTO registrationAdminDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            System.err.println("error!");
            Map<String, String> errors = new HashMap<>();
            for (FieldError error:bindingResult.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
        }
        try{
            adminService.registerAdmin(registrationAdminDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/bank")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getBloodBankOfAdmin(@RequestParam long id) {
        try {
            Admin admin = adminService.Read(id);
            return ResponseEntity.status(HttpStatus.OK).body(admin.getBloodBank());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

