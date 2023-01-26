package com.example.BloodBank.service;

import com.example.BloodBank.exceptions.EmailTakenException;
import com.example.BloodBank.exceptions.UsernameTakenException;
import com.example.BloodBank.model.*;
import com.example.BloodBank.service.service_interface.repository.HeadAdminRepository;
import com.example.BloodBank.service.service_interface.repository.UserRepository;
import com.example.BloodBank.service.service_interface.IHeadAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HeadAdminService implements IHeadAdminService {
    private final HeadAdminRepository headAdminRepository;
    private final UserRepository userRepository;
    @Autowired
    public HeadAdminService(HeadAdminRepository headAdminRepository, UserRepository userRepository){
        this.headAdminRepository = headAdminRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void registerHeadAdmin(HeadAdmin headAdmin) throws Exception{
        try {
            if(userRepository.findByUsername(headAdmin.getUsername()).isPresent())
                throw new UsernameTakenException(headAdmin.getUsername());
            if(userRepository.findByEmail(headAdmin.getEmail()).isPresent())
                throw new EmailTakenException(headAdmin.getUsername());

            Create(headAdmin);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    @Transactional
    public HeadAdmin Create(HeadAdmin entity) throws Exception {
        entity.setRole(Role.HEADADMIN);
        headAdminRepository.save(entity);
        return entity;
    }

    @Override
    public HeadAdmin Read(Long id) throws Exception {
        return headAdminRepository.findById(id).get();
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, allEntries = true, key = "#entity.getUsername()")
    public HeadAdmin Update(HeadAdmin entity) throws Exception {
        return headAdminRepository.save(entity);
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, allEntries = true, key = "#entity.getUsername()")
    public void Delete(HeadAdmin entity) throws Exception {

    }

    @Override
    public Iterable<HeadAdmin> GetAll() throws Exception {
        return null;
    }
    @Override
    public boolean isAdminWithNotChangedPassword(AuthRequest authRequest) {
        Optional<HeadAdmin> admin = headAdminRepository.findByUsername(authRequest.getUserName());
        if(admin.isPresent() && admin.get().getPassword().equals(authRequest.getPassword()) && !admin.get().isPasswordChanged())
            return true;
        return false;
    }

    @Override
    @Transactional
    public boolean resetAdminsPassword(HeadAdmin admin, String newPass){
        Optional<HeadAdmin> headAdmin = headAdminRepository.findByUsername(admin.getUsername());
        if(headAdmin.isPresent() && headAdmin.get().getPassword().equals(admin.getPassword()) && !headAdmin.get().isPasswordChanged()){
            HeadAdmin updatableAdmin = headAdmin.get();
            updatableAdmin.setPassword(newPass);
            updatableAdmin.setPasswordChanged(true);
            try{
                Update(updatableAdmin);
                return true;
            }catch (Exception e){
                throw new UnsupportedOperationException("Can not update head admin!");
            }
        }
        return false;
    }
}
