package com.example.BloodBank.dto;

import com.example.BloodBank.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationBloodBankDTO {
    @NotNull
    @NotBlank
    @Pattern(regexp="([A-Z][a-z]+)(\\s[A-Z]?[a-z]+)*", message="Invalid bank name input!")
    private String name;

    @NotBlank
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message="Invalid email address!" )
    private String email;

    @NotNull
    @Valid
    private Address address;
    private String description;
    private double rating;
    @NotNull
    @Min(0)
    @Max(24)
    private int startTime;
    @NotNull
    @Min(0)
    @Max(24)
    private int endTime;
}
