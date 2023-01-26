package com.example.BloodBank.dto;

import com.example.BloodBank.model.Address;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;

public class BloodBankDTO {
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

//    @Size(min = 1, max = 10, message="Max 10 admins for a bank allowed")
//    private ArrayList<Long> adminIDs;

    public BloodBankDTO(String name, String email, Address address, String description, double rating) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.description = description;
        this.rating = rating;
    }

    public BloodBankDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
