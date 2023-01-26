package com.example.BloodBank.service.service_interface.repository;

import com.example.BloodBank.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
