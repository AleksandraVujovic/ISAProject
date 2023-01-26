package com.example.BloodBank.service;

import com.example.BloodBank.dto.complaintDTOs.ComplaintDTO;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.*;
import com.example.BloodBank.service.service_interface.repository.ComplaintRepository;
import com.example.BloodBank.service.service_interface.IComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService implements IComplaintService {
    private final ComplaintRepository complaintRepository;
    private final CustomerService customerService;
    private final BloodBankService bloodBankService;
    private final HeadAdminService headAdminService;
    private final AdminService adminService;
    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository,CustomerService customerService,
                            BloodBankService bloodBankService, AdminService adminService,HeadAdminService headAdminService) {
        this.complaintRepository = complaintRepository;
        this.customerService = customerService;
        this.bloodBankService = bloodBankService;
        this.adminService = adminService;
        this.headAdminService = headAdminService;
    }

    @Override
    public Complaint Create(Complaint entity) throws Exception {
        entity.setCustomer(customerService.FindByUsername(entity.getCustomer().getUsername()));
        return complaintRepository.save(entity);
    }

    @Override
    public Complaint Read(Long id) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public Complaint Update(Complaint entity) throws Exception {
        Optional<Complaint> complaint = complaintRepository.findById(entity.getComplaintID());
        if(complaint.isPresent()){
            complaint.get().setComplaintStatus(ComplaintStatus.ANSWERED);
            complaint.get().setAnswer(entity.getAnswer());
            complaint.get().setHeadAdmin(headAdminService.Read(entity.getHeadAdmin().getId()));
            return complaintRepository.save(complaint.get());
        } else {
            throw new EntityDoesntExistException(entity.getComplaintID());
        }
    }

    @Override
    public void Delete(Complaint entity) throws Exception {

    }

    @Override
    public Iterable<Complaint> GetAll() throws Exception {
        return null;
    }

    @Override
    public List<Complaint> findAllByComplaintStatus(Pageable page) {
        Page<Complaint> pageComplaints =  complaintRepository.findAllByComplaintStatus(ComplaintStatus.UNANSWERED, page);
        List<Complaint> complaints = new ArrayList<>();
        for (Complaint c : pageComplaints) {
            complaints.add(c);
        }
        return complaints;
    }

    @Override
    public int getUnansweredComplaintsAmount() {
        List<Complaint> complaints = complaintRepository.getComplaintsByComplaintStatusAmount(ComplaintStatus.UNANSWERED);
        return complaints.size();
    }
    @Override
    public List<ComplaintDTO> getDefendantAndCustomerName(List<ComplaintDTO> complaintDTOS, List<Complaint> complaints) {
        for(ComplaintDTO c : complaintDTOS){
            Complaint realComplaint = complaints.get(complaintDTOS.indexOf(c));
            c.setCustomerName(realComplaint.getCustomer().getFirstName() + " " +
                    realComplaint.getCustomer().getLastName());
            c = getDefendantName(c, realComplaint);
        }
        return  complaintDTOS;
    }

    @Override
    public ComplaintDTO getDefendantName(ComplaintDTO complaintDTO, Complaint complaint) {
        if(complaint.getComplaintType().equals(ComplaintType.FACILITY))
            complaintDTO.setDefendantName(bloodBankService.findByEmail(complaint.getEmailOfDefendant()).get().getName());
        else{
            Admin admin = adminService.findByEmail(complaint.getEmailOfDefendant()).get();
            complaintDTO.setDefendantName(admin.getFirstName() + " " + admin.getLastName());
        }
        return complaintDTO;
    }

    public Optional<Complaint> findById(Number id) {
        if(!complaintRepository.findById(id).isPresent())
            throw new IllegalStateException("Customer with that kind of email doesn't exist!");
        return complaintRepository.findById(id);
    }

}
