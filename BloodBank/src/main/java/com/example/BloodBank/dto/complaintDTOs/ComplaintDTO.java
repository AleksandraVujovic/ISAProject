package com.example.BloodBank.dto.complaintDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long headAdminId;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String answer;
    @NotNull
    @NotBlank
    private String defendantName;
    @NotNull
    @NotBlank
    private String complaintType;
    @NotNull
    @NotBlank
    private String customerName;
    @Past
    private LocalDate submissionDate;


}
