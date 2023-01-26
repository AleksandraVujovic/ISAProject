package com.example.BloodBank.service.service_interface;

import com.example.BloodBank.model.Appointment;
import com.example.BloodBank.model.AuthRequest;
import com.example.BloodBank.model.BloodBank;
import com.example.BloodBank.model.HeadAdmin;

import java.util.Optional;

public interface IHeadAdminService extends ICRUDService<HeadAdmin>{
    void registerHeadAdmin(HeadAdmin headAdmin) throws Exception;
    boolean resetAdminsPassword(HeadAdmin admin, String newPass);
    Optional<HeadAdmin> findByUsername(String username);
}
