package com.example.BloodBank.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotBlank
    //@Pattern(regexp="([A-Z][a-z]+)(\\s[A-Z][a-z]+)*", message="Invalid country input!")
    private String country;

    @NotNull
    @NotBlank
    //@Pattern(regexp="([A-Z][a-z]+)(\\s[A-Z][a-z]+)*", message="Invalid city input!")
    private String city;

    @NotNull
    @NotBlank
    //@Pattern(regexp="([A-Z][a-z]+)(\\s[A-Z]?[a-z]+)*", message="Invalid street input!")
    private String street;
    @NotNull
    @NotBlank
    //@Pattern(regexp="([0-9]{1,3})[A-Z]?", message="Invalid street number input!")
    private String number;
    public Address() {
    }

    public Address(Long id, String country, String city, String street, String number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public Address(String country, String city, String street, String number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return street + " " + number + "," + city + "," + country;
    }
}
