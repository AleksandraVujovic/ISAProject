package com.example.BloodBank.repository;

import com.example.BloodBank.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
