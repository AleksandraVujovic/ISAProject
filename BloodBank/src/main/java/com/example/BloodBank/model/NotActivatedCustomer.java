package com.example.BloodBank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="not_activated_user")
@Table
public class NotActivatedCustomer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String username;
    private String password;
    @Column(unique=true)
    private String email;
    private Gender gender;
    private LocalDate dob;
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    private String activation;
    private String jmbg;
    private String phoneNumber;
    private String profession;
    private String professionInfo;
}
