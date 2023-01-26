package com.example.BloodBank.service.service_interface.repository;

import com.example.BloodBank.model.Customer;
import com.example.BloodBank.model.HeadAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeadAdminRepository extends JpaRepository<HeadAdmin, Long> {
    Optional<HeadAdmin> findByUsername(String username);
}
