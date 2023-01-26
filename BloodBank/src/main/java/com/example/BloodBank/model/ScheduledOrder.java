package com.example.BloodBank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class ScheduledOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int dayOfMonth;
    private int Aplus;
    private int Bplus;
    private int ABplus;
    private int Oplus;
    private int Aminus;
    private int Bminus;
    private int ABminus;
    private int Ominus;
    private String bankEmail;
    private String hospitalEmail;
    public ScheduledOrder(){}

    public ScheduledOrder(Long id, int dayOfMonth,
                          int aplus, int bplus, int ABplus,
                          int oplus, int aminus, int bminus,
                          int ABminus, int ominus,
                          String bankEmail, String hospitalEmail) {
        this.id = id;
        this.dayOfMonth = dayOfMonth;
        Aplus = aplus;
        Bplus = bplus;
        this.ABplus = ABplus;
        Oplus = oplus;
        Aminus = aminus;
        Bminus = bminus;
        this.ABminus = ABminus;
        Ominus = ominus;
        this.bankEmail = bankEmail;
        this.hospitalEmail = hospitalEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getAplus() {
        return Aplus;
    }

    public void setAplus(int aplus) {
        Aplus = aplus;
    }

    public int getBplus() {
        return Bplus;
    }

    public void setBplus(int bplus) {
        Bplus = bplus;
    }

    public int getABplus() {
        return ABplus;
    }

    public void setABplus(int ABplus) {
        this.ABplus = ABplus;
    }

    public int getOplus() {
        return Oplus;
    }

    public void setOplus(int oplus) {
        Oplus = oplus;
    }

    public int getAminus() {
        return Aminus;
    }

    public void setAminus(int aminus) {
        Aminus = aminus;
    }

    public int getBminus() {
        return Bminus;
    }

    public void setBminus(int bminus) {
        Bminus = bminus;
    }

    public int getABminus() {
        return ABminus;
    }

    public void setABminus(int ABminus) {
        this.ABminus = ABminus;
    }

    public int getOminus() {
        return Ominus;
    }

    public void setOminus(int ominus) {
        Ominus = ominus;
    }

    public String getBankEmail() {
        return bankEmail;
    }

    public void setBankEmail(String bankEmail) {
        this.bankEmail = bankEmail;
    }

    public String getHospitalEmail() {
        return hospitalEmail;
    }

    public void setHospitalEmail(String hospitalEmail) {
        this.hospitalEmail = hospitalEmail;
    }

    @Override
    public String toString() {
        return "ScheduledOrder{" +
                "id=" + id +
                ", dayOfMonth=" + dayOfMonth +
                ", Aplus=" + Aplus +
                ", Bplus=" + Bplus +
                ", ABplus=" + ABplus +
                ", Oplus=" + Oplus +
                ", Aminus=" + Aminus +
                ", Bminus=" + Bminus +
                ", ABminus=" + ABminus +
                ", Ominus=" + Ominus +
                ", bankEmail='" + bankEmail + '\'' +
                ", hospitalEmail='" + hospitalEmail + '\'' +
                '}';
    }
}
