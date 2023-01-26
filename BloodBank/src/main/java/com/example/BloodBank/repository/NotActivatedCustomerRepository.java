package com.example.BloodBank.repository;

import com.example.BloodBank.model.NotActivatedCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotActivatedCustomerRepository extends JpaRepository<NotActivatedCustomer, Long> {
Optional<NotActivatedCustomer> findByActivation(String activationCode);
}
