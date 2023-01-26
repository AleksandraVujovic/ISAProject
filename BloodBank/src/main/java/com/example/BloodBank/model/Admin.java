package com.example.BloodBank.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Admin extends User implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id", referencedColumnName = "bankID")
    private BloodBank bloodBank;
    public Admin(String firstName, String lastName, String username, String password, String email, Gender gender,
                 LocalDate dob, Role role, Address address, BloodBank bloodBank) {
        super(firstName, lastName, username, password, email, gender, dob, role, address);
        this.bloodBank = bloodBank;
    }

    public Admin(BloodBank bloodBank) {
        this.bloodBank = bloodBank;
    }

    public Admin() {
    }

    public BloodBank getBloodBank() {
        return bloodBank;
    }

    public void setBloodBank(BloodBank bloodBank) {
        this.bloodBank = bloodBank;
    }
}
