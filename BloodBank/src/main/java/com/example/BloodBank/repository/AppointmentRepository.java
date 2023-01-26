package com.example.BloodBank.repository;

import com.example.BloodBank.model.Admin;
import com.example.BloodBank.model.Appointment;
import com.example.BloodBank.model.AppointmentStatus;
import com.example.BloodBank.model.Complaint;
import com.example.BloodBank.model.ComplaintStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByConfirmationCode(String confirmationCode);

    @Query("SELECT appointment FROM appointment appointment \n" +
            "WHERE (appointment.takenBy.id = :id) AND :Months6 < appointment.appointmentDate")
    List<Appointment> findAllAppointmentsIn6MonthPeriod(@Param("id") long customerId, @Param("Months6") Date months6);

    @Query("SELECT appointment FROM appointment appointment \n" +
            "WHERE appointment.executed = 'FREE'")
    Page<Appointment> findAllAvailable(Pageable page);

    @Query("SELECT appointment FROM appointment appointment \n" +
            "WHERE appointment.takenBy.id = :id")
    List<Appointment> findByCustomerId(@Param("id") long customerId);
    @Query("select a from appointment a where (a.executed = 'DONE' or a.executed = 'PENDING') and  a.location.bankID = ?1")
    List<Appointment> findDoneAndPendingAppointmentsByBankId(Long bankId);

    @Query("SELECT appointment FROM appointment appointment \n" +
            "WHERE appointment.executed = 'FREE' AND appointment.appointmentDate = :startDate AND appointment.startTime = :startTime")
    Page<Appointment> findAllAvailableDateFilter(Pageable page, @Param("startDate") Date startDate, @Param("startTime") Time startTime);
}
