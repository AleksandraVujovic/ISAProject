package com.example.BloodBank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity()
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

//    @NotNull
//    @NotBlank
    private Boolean donated;
    private Boolean denied;
    private Boolean healthy;
    private Boolean eaten;
    private Boolean dangerousJob;
    //FEMALES ONLY
    private Boolean secondState;
    private Boolean menstruating;
    private Boolean pregnant;
    private String donorNumber;
    private LocalDate fillDate;

    public boolean checkIfValidForReservationOfAppointment() {
        return fillDate.isAfter(LocalDate.now().minusMonths(1));
    }
}
