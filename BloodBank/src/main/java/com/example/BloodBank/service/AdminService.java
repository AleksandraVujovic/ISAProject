package com.example.BloodBank.service;

import com.example.BloodBank.dto.userDTOs.RegistrationAdminDTO;
import com.example.BloodBank.model.BloodBank;
import com.example.BloodBank.service.service_interface.repository.AdminRepository;
import com.example.BloodBank.service.service_interface.IAdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.Admin;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final BloodBankService bloodBankService;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AdminService(AdminRepository adminRepository, BloodBankService bloodBankService){
        this.adminRepository = adminRepository;
        this.bloodBankService = bloodBankService;
    }

    @Override
    public Admin Create(Admin entity){
        return adminRepository.save(entity);

    }

    @Override
    public Admin Read(Long id) throws Exception {
        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isPresent()){
            return admin.get();
        } else {
            throw new EntityDoesntExistException(id);
        }
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, allEntries = true, key = "#entity.getUsername()")
    public Admin Update(Admin entity){
        return adminRepository.save(entity);
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, allEntries = true, key = "#entity.getUsername()")
    public void Delete(Admin entity){
        adminRepository.delete(entity);
    }

    @Override
    public Iterable<Admin> GetAll() {
        return adminRepository.findAll();
    }


   // @Override
  //  public Optional<Admin> findByBloodBankId(long id) {
     //   return adminRepository.findByBlodBankId(id);
   // }

    @Transactional
    @Override
    public void registerAdmin(RegistrationAdminDTO registrationAdminDTO) {

        try{
            Admin admin = modelMapper.map(registrationAdminDTO, Admin.class);
            Optional<BloodBank> bank = bloodBankService.findByEmail(registrationAdminDTO.getBankEmail());
            if(!bank.isPresent())
                throw new IllegalStateException();
            admin.setBloodBank(bank.get());
            adminRepository.save(admin);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException("Can't save admin!");
        }
    }
    public Optional<Admin> findByEmail(String email) {
        if(!adminRepository.findByEmail(email).isPresent())
            throw new IllegalStateException("Customer with that kind of email doesn't exist!");
        return adminRepository.findByEmail(email);
    }
}
