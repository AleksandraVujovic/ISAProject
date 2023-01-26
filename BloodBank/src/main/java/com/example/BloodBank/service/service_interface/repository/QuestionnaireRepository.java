package com.example.BloodBank.service.service_interface.repository;

import com.example.BloodBank.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    Questionnaire findByCustomerId(long id);
}
