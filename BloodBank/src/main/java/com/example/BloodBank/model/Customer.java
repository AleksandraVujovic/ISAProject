package com.example.BloodBank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Customer extends User implements Serializable {
    @NotNull
    @NotBlank
    private String jmbg;
    @NotNull
    @NotBlank
    private String phoneNumber;
    @NotNull
    @NotBlank
    private String profession;
    @NotNull
    @NotBlank
    private String professionInfo;

    @OneToMany(mappedBy = "takenBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Appointment> appointments;
    public Customer(Long id, String firstName, String lastName,
                    String username, String password,
                    String email, Gender gender, LocalDate dob,
                    Role role, Address address) {
        super(id, firstName, lastName, username, password,
                email, gender, dob, role, address);
    }

    public Customer(String firstName, String lastName,
                    String username, String password,
                    String email, Gender gender, LocalDate dob,
                    Role role, Address address) {
        super(firstName, lastName, username, password, email,
                gender, dob, role, address);
    }

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName,
                    String username, String password, String email,
                    Gender gender, LocalDate dob, Role role,
                    Address address, String jmbg, String phoneNumber,
                    String profession, String professionInfo) {
        super(id, firstName, lastName, username, password,
                email, gender, dob, role, address);
        this.jmbg = jmbg;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
        this.professionInfo = professionInfo;
    }

    public Customer(String firstName, String lastName, String username,
                    String password, String email, Gender gender,
                    LocalDate dob, Role role, Address address, String jmbg,
                    String phoneNumber, String profession, String professionInfo) {
        super(firstName, lastName, username, password,
                email, gender, dob, role, address);
        this.jmbg = jmbg;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
        this.professionInfo = professionInfo;
    }

    public Customer(String jmbg, String phoneNumber, String profession, String professionInfo) {
        this.jmbg = jmbg;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
        this.professionInfo = professionInfo;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfessionInfo() {
        return professionInfo;
    }

    public void setProfessionInfo(String professionInfo) {
        this.professionInfo = professionInfo;
    }

    /*public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }*/
}
