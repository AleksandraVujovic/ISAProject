package com.example.BloodBank.service.service_interface.repository;

import com.example.BloodBank.model.Admin;
import com.example.BloodBank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

}
