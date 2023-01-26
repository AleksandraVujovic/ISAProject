package com.example.BloodBank.repository;

import com.example.BloodBank.model.ScheduledOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledOrdersRepository extends JpaRepository<ScheduledOrder, Long> {
}
