package com.example.BloodBank.service.service_interface;

import com.example.BloodBank.model.Questionnaire;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface IQuestionnaireService {

    boolean checkQuestionnaire(long id);

    @Cacheable(value = "questionnaire", key = "#id")
    Questionnaire getForCustomer(long id) throws Exception;

    @CacheEvict(cacheNames = {"questionnaire", "questionnaires"}, allEntries = true)
    Questionnaire Create(Questionnaire entity) throws Exception;

    Questionnaire Read(Long id) throws Exception;

    @CacheEvict(cacheNames = {"questionnaire", "questionnaires"}, allEntries = true)
    Questionnaire Update(Questionnaire entity) throws Exception;

    @CacheEvict(cacheNames = {"questionnaire", "questionnaires"}, allEntries = true)
    void Delete(Questionnaire entity) throws Exception;


    @Cacheable("questionnaires")
    Iterable<Questionnaire> GetAll() throws Exception;
}
