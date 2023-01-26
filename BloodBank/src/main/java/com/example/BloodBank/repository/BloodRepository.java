package com.example.BloodBank.repository;

import com.example.BloodBank.model.Blood;
import com.example.BloodBank.model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodRepository extends JpaRepository<Blood, Long> {
}
