package com.example.BloodBank.repository;

import com.example.BloodBank.model.BloodBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    Optional<BloodBank> findByEmail(String email);

    @Query("SELECT bank FROM blood_bank bank \n" +
            "WHERE lower(bank.name) LIKE %?1%")
    Page<BloodBank> findAllByName(String name, Pageable page);

    @Query("SELECT bank FROM blood_bank bank \n" +
            "INNER JOIN bank.address address\n" +
            "LEFT JOIN bank.blood blood\n" +
            "WHERE (CONCAT(lower(address.street), ' ', lower(address.number), ' ' ,lower(address.city),' ' ,lower(address.country)))\n" +
            "LIKE %?1%")
    Page<BloodBank> findByAddress(String address, Pageable page);

    @Query("SELECT bank FROM blood_bank bank \n" +
            "WHERE bank.rating >= ?1 and bank.rating <= ?2")
    Page<BloodBank> findByRatingRange(double bottom, double top, Pageable page);
}
