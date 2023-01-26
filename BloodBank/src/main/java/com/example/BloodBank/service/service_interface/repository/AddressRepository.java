package com.example.BloodBank.service.service_interface.repository;

import com.example.BloodBank.model.Address;
import com.example.BloodBank.model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
