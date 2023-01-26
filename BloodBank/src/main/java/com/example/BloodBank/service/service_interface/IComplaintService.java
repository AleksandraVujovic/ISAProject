package com.example.BloodBank.service.service_interface;

import com.example.BloodBank.dto.complaintDTOs.ComplaintDTO;
import com.example.BloodBank.model.Complaint;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IComplaintService extends ICRUDService<Complaint>{
    List<Complaint> findAllByComplaintStatus(Pageable page);
    int getUnansweredComplaintsAmount();
    List<ComplaintDTO> getDefendantAndCustomerName(List<ComplaintDTO> complaintDTOS, List<Complaint> complaints);
    ComplaintDTO getDefendantName(ComplaintDTO complaintDTO, Complaint complaint);
    Complaint save(Complaint complaintToUpdate);
    Optional<Complaint> findById(long id);
}
