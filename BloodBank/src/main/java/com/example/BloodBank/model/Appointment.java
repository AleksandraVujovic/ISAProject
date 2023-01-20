package com.example.BloodBank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;


@Entity(name="appointment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private Date appointmentDate;

    @Column(nullable = true)
    private String comment;

    @Column(nullable = true)
    private TypeOfBlood typeOfBlood;

    private int quantityOfBlood;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;

    @Column(nullable = true, updatable = true)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus executed;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = true)
    private Customer takenBy;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bloodBank_id", referencedColumnName = "bankID", nullable = false)
    private BloodBank location;

    @Version
    private Integer version;

    private String confirmationCode;

    public Appointment(Date appointmentDate, Time startTime, Time endTime, Customer takenBy, BloodBank location, AppointmentStatus status) {
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.takenBy = takenBy;
        this.location = location;
        this.executed = status;
    }

    public boolean validate() {
        return startTime.after(location.getStartDayWorkTime())
                && startTime.before(location.getEndDayWorkTime())
                && endTime.after(location.getStartDayWorkTime())
                && endTime.before(location.getEndDayWorkTime());
    }

}
