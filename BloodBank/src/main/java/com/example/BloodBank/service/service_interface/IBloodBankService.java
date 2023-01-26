package com.example.BloodBank.service.service_interface;

import com.example.BloodBank.dto.BloodBankDTO;
import com.example.BloodBank.model.Appointment;
import com.example.BloodBank.model.BloodBank;
import com.example.BloodBank.model.ScheduledOrder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import java.util.Optional;


public interface IBloodBankService  {


    @CacheEvict(cacheNames = {"bloodBanks", "bloodBanksPageable"}, allEntries = true)
    BloodBank Create(BloodBank entity) throws Exception;

    @Cacheable(value = "bloodBank", key ="#id")
    BloodBank Read(Long id) throws Exception;
    @Caching(evict = {
            @CacheEvict(cacheNames = {"bloodBanks", "bloodBanksPageable"}, allEntries = true),
            @CacheEvict(value = "bloodBank", key = "#entity.getBankID()")
    })
    BloodBank Update(BloodBank entity) throws Exception;

    @Caching(evict = {
            @CacheEvict(cacheNames = {"bloodBanks", "bloodBanksPageable"}, allEntries = true),
            @CacheEvict(value = "bloodBank", key = "#entity.getBankID()")
    })
    void Delete(BloodBank entity) throws Exception;

    @Cacheable(value = "bloodBanks")
    Iterable<BloodBank> GetAll() throws Exception;
    boolean checkForBlood(String bankEmail, String bloodType, int quantity);
    void checkAPIKey(String bankEmail, String APIKey) throws IllegalAccessException;

    @CacheEvict(cacheNames = {"bloodBanks", "bloodBanksPageable"}, allEntries = true)
    void registerBloodBank(BloodBankDTO bloodBank);
    @Cacheable(value = "bloodBanks")
    List<BloodBank> getAll() throws Exception;


    Page<BloodBank> getBanksByRatingRange(String filter, Pageable page) throws Exception;


    @Cacheable(value = "bloodBanksPageable", condition = "#filter.isEmpty() && #page.getPageNumber() == 0",key="new org.springframework.cache.interceptor.SimpleKey(#root.methodName, #filter, #page.getPageNumber(), #page.getPageSize())")
    Page<BloodBank> getBanksByName(String filter, Pageable page) throws Exception;

    Page<BloodBank> getBanksByAddress(String filter, Pageable page) throws Exception;

    Optional<BloodBank> findByEmail(String email);

    Integer sendBlood(String bankEmail, String bloodType, int quantity);
    Boolean savePDF(String bankEmail, byte[] pdf);

    boolean checkIfBloodSupplyAvailable(String bankEmail, ScheduledOrder order);
    void reduceBloodSupply(String bankEmail, ScheduledOrder order) throws Exception;

    void saveBloodFromAppointment(Appointment entity);
}
