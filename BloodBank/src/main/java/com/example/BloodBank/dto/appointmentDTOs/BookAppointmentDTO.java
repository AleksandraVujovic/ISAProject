package com.example.BloodBank.dto.appointmentDTOs;

import com.example.BloodBank.model.Appointment;
import com.example.BloodBank.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookAppointmentDTO {
    public long appointmentId;
    public long customerId;

}
