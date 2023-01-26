package com.example.BloodBank.controller;

import adapters.CustomerMapper;
import adapters.HeadAdminMapper;
import com.example.BloodBank.dto.userDTOs.RegisterHeadAdminDTO;
import com.example.BloodBank.dto.userDTOs.ResetAdminsPasswordDTO;
import com.example.BloodBank.service.HeadAdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/head_admin")
@CrossOrigin(origins = "http://localhost:4200")
public class HeadAdminController {
    private final HeadAdminService headAdminService;
    private final ModelMapper modelMapper;
    private HeadAdminMapper headAdminMapper;
    @Autowired
    public HeadAdminController(HeadAdminService headAdminService, ModelMapper modelMapper){
        this.headAdminService = headAdminService;
        this.modelMapper = modelMapper;
        this.headAdminMapper = new HeadAdminMapper(modelMapper);
    }
    @PostMapping(consumes = "application/json",
            produces = "application/json"
    )
    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasRole('ROLE_HEADADMIN')")
    public ResponseEntity<Object> registerHeadAdmin(@Valid @RequestBody RegisterHeadAdminDTO headAdminDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            System.err.println("error!");
            Map<String, String> errors = new HashMap<>();
            for (FieldError error:bindingResult.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            headAdminService.registerHeadAdmin(headAdminMapper.fromDTO(headAdminDTO));
            return new ResponseEntity<>(true,
                                HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = "application/json",
            produces = "application/json",
            path = "change_pass"
    )
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<Object> resetAdminsPassword(@Valid @RequestBody ResetAdminsPasswordDTO resetAdminsPasswordDTO){
        try {
            return new ResponseEntity<>(headAdminService.resetAdminsPassword(headAdminMapper.fromDTO(resetAdminsPasswordDTO), resetAdminsPasswordDTO.getNewPassword()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
