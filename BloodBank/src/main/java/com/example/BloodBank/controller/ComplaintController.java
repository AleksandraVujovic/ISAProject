package com.example.BloodBank.controller;

import adapters.ComplaintMapper;
import adapters.CreationComplaintMapper;
import com.example.BloodBank.dto.complaintDTOs.ComplaintDTO;
import com.example.BloodBank.dto.complaintDTOs.CreationComplaintDTO;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.Complaint;
import com.example.BloodBank.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/complaint")
@CrossOrigin("http://localhost:4200")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final ModelMapper modelMapper;
    private CreationComplaintMapper creationComplaintMapper;
    private ComplaintMapper complaintMapper;

    @Autowired
    public ComplaintController(ComplaintService complaintService, ModelMapper modelMapper) {

        this.complaintService = complaintService;
        this.modelMapper = modelMapper;
        this.creationComplaintMapper = new CreationComplaintMapper(modelMapper);
        this.complaintMapper = new ComplaintMapper(modelMapper);
    }


    @Operation(summary = "Create a complaint", description = "Create a complaint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Complaint successfully created",
                    content =
                            { @Content(mediaType = "application/json", schema = @Schema(implementation = CreationComplaintDTO.class)) }
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @PostMapping
    public ResponseEntity<Object> createComplaint(@Valid @RequestBody CreationComplaintDTO creationComplaintDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            System.err.println("error!");
            Map<String, String> errors = new HashMap<>();
            for (FieldError error:bindingResult.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
        }
        try{
            complaintService.Create(creationComplaintMapper.fromCreationComplaintDTO(creationComplaintDTO));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ComplaintDTO>> getComplaints(@RequestParam("page") Optional<String> pageNumber,
                                                            @RequestParam("size") Optional<String> size) {
        try {
            Pageable page;
            page = PageRequest.of(Integer.valueOf(pageNumber.get()), Integer.valueOf(size.get()));
            List<Complaint> complaints = complaintService.findAllByComplaintStatus(page);
            return new ResponseEntity<>(complaintService.getDefendantAndCustomerName(complaintMapper.toDTOList(complaints), complaints), HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="amount")
    public ResponseEntity<Integer> getNumberOfUnansweredComplaints() {
        try {
            int amount = complaintService.getUnansweredComplaintsAmount();
            return new ResponseEntity<>(amount, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplaintDTO> FindByID(@PathVariable("id") Long id){
        try {
            ComplaintDTO complaintDTO = complaintMapper.toDTO(complaintService.findById(id).get());
            return new ResponseEntity<>(complaintService.getDefendantName(complaintDTO, complaintService.findById(id).get()), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplaintDTO> Update(@Valid @RequestBody ComplaintDTO complaintDTO){
        try {
            Complaint complaint = complaintMapper.fromDTO(complaintDTO);
            complaintService.Update(complaint);
            return new ResponseEntity<>(complaintDTO, HttpStatus.OK);
        } catch (Exception e){
            if(e instanceof EntityDoesntExistException){
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
