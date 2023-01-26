package com.example.BloodBank.service;

import com.example.BloodBank.dto.QuestionnaireDTO;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.Customer;
import com.example.BloodBank.exceptions.QuestionnaireForCustomerDoesntExist;
import com.example.BloodBank.model.Questionnaire;
import com.example.BloodBank.service.service_interface.repository.CustomerRepository;
import com.example.BloodBank.service.service_interface.repository.QuestionnaireRepository;
import com.example.BloodBank.service.service_interface.IQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
public class QuestionnaireService implements IQuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository,
                                CustomerRepository customerRepository) {
        this.questionnaireRepository = questionnaireRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Questionnaire Create(Questionnaire entity) throws Exception {
        return questionnaireRepository.save(entity);
    }

    @CacheEvict(value = {"questionnaire", "questionnaires"}, allEntries = true)
    public Questionnaire CreateDTO(QuestionnaireDTO dto){
        Questionnaire questionnaire = questionnaireRepository.findByCustomerId((Long.valueOf(dto.getCustomerId())));
        if (questionnaire == null){
            questionnaire = new Questionnaire();
        }
        Customer customer = customerRepository.findById(Long.valueOf(dto.getCustomerId())).get();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YY-MM-DD");
//        formatter = formatter.withLocale( putAppropriateLocaleHere );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(dto.getDate());

        questionnaire.setCustomer(customer);
        questionnaire.setDenied(dto.getDenied());
        questionnaire.setEaten(dto.getEaten());
        questionnaire.setDonated(dto.getDonated());
        questionnaire.setHealthy(dto.getHealthy());
        questionnaire.setDangerousJob(dto.getDangerousJob());
        questionnaire.setPregnant(dto.getPregnant());
        questionnaire.setDonorNumber(dto.getDonorNumber());
        questionnaire.setFillDate(date);
        questionnaire.setMenstruating(dto.getMenstruating());
        questionnaire.setSecondState(dto.getSecondState());
        questionnaireRepository.save(questionnaire);
        return  questionnaire;
    }

    @Override
    public Questionnaire Read(Long id) throws Exception {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        if(questionnaire.isPresent()){
            return questionnaire.get();
        } else {
            throw new EntityDoesntExistException(id);
        }
    }

    @Override
    public Questionnaire Update(Questionnaire entity) throws Exception {
        //NOT NEEDED AT THE MOMENT
//        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(entity.getId());
//        if(questionnaire.isPresent()){
//            Questionnaire temp = questionnaire.get();
//            temp.up
//
//            return questionnaireRepository.save(temp);
//        }
        return questionnaireRepository.save(entity);
    }

    @Override
    public void Delete(Questionnaire entity) throws Exception {
        questionnaireRepository.delete(entity);
    }

    @Override
    public Iterable<Questionnaire> GetAll() throws Exception {
        return questionnaireRepository.findAll();
    }

    @Override
    public boolean checkQuestionnaire(long id) {
        Questionnaire questionnaire = questionnaireRepository.findByCustomerId(id);
        if(questionnaire == null){
            return false;
        }
        return questionnaire.checkIfValidForReservationOfAppointment();
    }

    @Override
    public Questionnaire getForCustomer(long id) throws Exception {
        Questionnaire questionnaire = questionnaireRepository.findByCustomerId(id);
        if(questionnaire == null){
            throw new QuestionnaireForCustomerDoesntExist();
        }
        return questionnaire;
    }
}
