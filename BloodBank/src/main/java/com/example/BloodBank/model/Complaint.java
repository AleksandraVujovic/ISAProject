package com.example.BloodBank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity(name="complaints")
@Table
public class Complaint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="complaint_id")
    private long complaintID;
    @Column(nullable = false)
    private String description;
    @Column
    private String answer;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "head_admin_id", referencedColumnName = "id", nullable = true)
    private HeadAdmin headAdmin;
    @NotNull
    private LocalDate submissionDate ;
    @NotNull
    private ComplaintType complaintType;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ComplaintStatus complaintStatus;
    @NotNull
    private String emailOfDefendant;
    public Complaint() {
    }

    public Complaint(long complaintID, String description, Customer customer, HeadAdmin headAdmin, LocalDate submissionDate,
                     ComplaintType complaintType, ComplaintStatus complaintStatus, String emailOfDefendant, String answer) {
        this.complaintID = complaintID;
        this.description = description;
        this.customer = customer;
        this.headAdmin = headAdmin;
        this.submissionDate = submissionDate;
        this.complaintType = complaintType;
        this.emailOfDefendant = emailOfDefendant;
        this.complaintStatus = complaintStatus;
        this.answer = answer;
    }
    public Complaint(String description, Customer customer, HeadAdmin headAdmin, LocalDate submissionDate,
                     ComplaintType complaintType, ComplaintStatus complaintStatus, String emailOfDefendant, String answer) {
        this.description = description;
        this.customer = customer;
        this.headAdmin = headAdmin;
        this.submissionDate = submissionDate;
        this.complaintType = complaintType;
        this.emailOfDefendant = emailOfDefendant;
        this.complaintStatus = complaintStatus;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ComplaintStatus getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(ComplaintStatus complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public ComplaintType getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(ComplaintType complaintType) {
        this.complaintType = complaintType;
    }

    public String getEmailOfDefendant() {
        return emailOfDefendant;
    }

    public void setEmailOfDefendant(String emailOfDefendant) {
        this.emailOfDefendant = emailOfDefendant;
    }

    public long getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(long complaintID) {
        this.complaintID = complaintID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public HeadAdmin getHeadAdmin() {
        return headAdmin;
    }

    public void setHeadAdmin(HeadAdmin headAdmin) {
        this.headAdmin = headAdmin;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
}
