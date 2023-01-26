package com.example.BloodBank.service.service_interface.repository;

import com.example.BloodBank.model.BloodBank;
import com.example.BloodBank.model.Complaint;
import com.example.BloodBank.model.ComplaintStatus;
import com.example.BloodBank.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  ComplaintRepository extends JpaRepository<Complaint, Long> {
    @Query("select c from complaints c where c.complaintStatus like :status")
    Page<Complaint> findAllByComplaintStatus(@Param("status") ComplaintStatus status, Pageable pageable);

    @Query("select c from complaints c where c.complaintStatus like :status")
    List<Complaint> getComplaintsByComplaintStatusAmount(@Param("status") ComplaintStatus status);

    Optional<Complaint> findById(Number id);
}
