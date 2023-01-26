package com.example.BloodBank.service.service_interface;

import com.example.BloodBank.dto.appointmentDTOs.BookAppointmentDTO;
import com.example.BloodBank.model.Appointment;
import com.example.BloodBank.model.BloodBank;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.sql.Time;

public interface IAppointmentService extends ICRUDService<Appointment> {
    List<Appointment> GetByCustomerId(Long id) throws Exception;

    Iterable<Appointment> GetForCustomerId(String id);

    Page<Appointment> GetAllPageableFreeDateFilter(Pageable page, Date startDate, Time startTime) throws Exception;

    List<Appointment> getDoneAndPendingAppointmentsForBloodBank(Long bankId);

    Page<Appointment> GetAllPageable(Pageable page) throws Exception;

    Page<Appointment> GetAllPageableFree(Pageable page) throws Exception;

    Boolean BookAppointment(BookAppointmentDTO dto) throws Exception;

    Appointment CancelAppointment(BookAppointmentDTO dto) throws Exception;

    Appointment ConfirmAppointment(String confirmationCode) throws Exception;

}
