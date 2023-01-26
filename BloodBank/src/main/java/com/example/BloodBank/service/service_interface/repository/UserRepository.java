package com.example.BloodBank.service.service_interface.repository;

import com.example.BloodBank.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from users u where lower(u.firstName) like %?1% or lower(u.lastName) like %?1%")
    Page<User> findAllByFirstNameOrLastName(String searchTerm, Pageable pageable);
    @Query("select u from users u where (lower(u.firstName) like %?1% or lower(u.lastName) like %?1%) and u.role = 2")
    Page<User> findAllCustomersByFirstNameOrLastName(String searchTerm, Pageable pageable);
    @Query("select u from users u where lower(u.firstName) like %?1% or lower(u.lastName) like %?1%")
    List<User> findAllBySearch(String searchTerm);
    @Query("select u from users u where (lower(u.firstName) like %?1% or lower(u.lastName) like %?1%) and u.role = 2")
    List<User> findAllCustomersBySearch(String searchTerm);


    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
