package com.example.BloodBank.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "blood")
public class Blood implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int Aplus;
    private int Bplus;
    private int ABplus;
    private int Oplus;
    private int Aminus;
    private int Bminus;
    private int ABminus;
    private int Ominus;

    public Blood() {
    }

    public Blood(Long id, int aplus, int bplus, int ABplus, int oplus, int aminus, int bminus,
                 int ABminus, int ominus) {
        this.id = id;
        Aplus = aplus;
        Bplus = bplus;
        this.ABplus = ABplus;
        Oplus = oplus;
        Aminus = aminus;
        Bminus = bminus;
        this.ABminus = ABminus;
        Ominus = ominus;
    }

    public Blood(int aplus, int bplus, int ABplus, int oplus,
                 int aminus, int bminus, int ABminus, int ominus) {
        Aplus = aplus;
        Bplus = bplus;
        this.ABplus = ABplus;
        Oplus = oplus;
        Aminus = aminus;
        Bminus = bminus;
        this.ABminus = ABminus;
        Ominus = ominus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
