package com.example.BloodBank.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
//    @NotNull
//    @NotBlank
    @Column(unique=true)
    private String username;
//    @NotNull
//    @NotBlank
    private String password;
    @Column(unique=true)
//    @NotNull
//    @NotBlank
//    @Email
    private String email;
    private Gender gender;
    private LocalDate dob;
//    @NotNull
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    
    public User(Long id, String firstName, String lastName, String username, String password, String email,
                Gender gender, LocalDate dob, Role role, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.role = role;
        this.address = address;
    }

    public User(String firstName, String lastName, String username, String password, String email, Gender gender, LocalDate dob, Role role, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.role = role;
        this.address = address;
    }

    public User() {
    }

    public void updateUserInfo(User user){
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.gender = user.gender;
        this.dob = user.dob;
        this.role = user.role;
        this.address = user.address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
