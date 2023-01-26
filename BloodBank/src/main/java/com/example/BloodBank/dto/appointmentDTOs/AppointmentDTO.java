package com.example.BloodBank.dto.appointmentDTOs;

import com.example.BloodBank.dto.BloodBankDTO;
import com.example.BloodBank.dto.userDTOs.CustomerDTO;

import com.example.BloodBank.model.AppointmentStatus;
import com.example.BloodBank.model.BloodBank;
import com.example.BloodBank.model.Customer;
import com.example.BloodBank.model.TypeOfBlood;

import java.sql.Date;
import java.sql.Time;

public class AppointmentDTO {
    private long id;

    private Date appointmentDate;

    private Time startTime;

    private Time endTime;

    private AppointmentStatus executed;
    private String comment;


    private TypeOfBlood typeOfBlood;
    private int quantityOfBlood;
    private Customer takenBy;

    private BloodBank location;

    public AppointmentDTO(long id, Date appointmentDate, Time startTime, Time endTime, Customer takenBy
                        //  ,BloodBank location
    ) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.takenBy = takenBy;
        //this.location = location;
    }

    public AppointmentDTO() {
    }

    public AppointmentStatus getExecuted() {
        return executed;
    }

    public void setExecuted(AppointmentStatus executed) {
        this.executed = executed;
    }
    public int getQuantityOfBlood() {
        return quantityOfBlood;
    }

    public void setQuantityOfBlood(int quatityOfBlood) {
        this.quantityOfBlood = quatityOfBlood;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TypeOfBlood getTypeOfBlood() {
        return typeOfBlood;
    }

    public void setTypeOfBlood(TypeOfBlood typeOfBlood) {
        this.typeOfBlood = typeOfBlood;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Customer getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(Customer takenBy) {
        this.takenBy = takenBy;
    }
//
//    public BloodBank getLocation() {
//        return location;
//    }
//
//    public void setLocation(BloodBank location) {
//        this.location = location;
//    }
}
