package com.example.BloodBank.service;

import com.example.BloodBank.dto.NewsDTO;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.BloodBank;
import com.example.BloodBank.model.News;
import com.example.BloodBank.service.service_interface.repository.BloodBankRepository;
import com.example.BloodBank.service.service_interface.repository.NewsRepository;
import com.example.BloodBank.service.service_interface.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsService implements INewsService {
    private final NewsRepository newsRepository;
    private final BloodBankRepository bloodBankRepository;
    private final RabbitMQSender rabbitMQSender;
    @Autowired
    public NewsService(NewsRepository newsRepository,
                       BloodBankRepository bloodBankRepository,
                       RabbitMQSender rabbitMQSender) {
        this.newsRepository = newsRepository;
        this.bloodBankRepository = bloodBankRepository;
        this.rabbitMQSender = rabbitMQSender;
    }
    @Override
    public News Create(News entity) throws Exception {
        return newsRepository.save(entity);
    }

    @Override
    public News Read(Long id) throws Exception {
        Optional<News> news = newsRepository.findById(id);
        if(news.isPresent()){
            return news.get();
        } else  {
            throw new EntityDoesntExistException(id);
        }
    }

    @Override
    public News Update(News entity) throws Exception {
        return newsRepository.save(entity);
    }

    @Override
    public void Delete(News entity) throws Exception {
        newsRepository.delete(entity);
    }

    @Override
    public Iterable<News> GetAll() throws Exception {
        return newsRepository.findAll();
    }
    public News CreateAndSendNews(NewsDTO dto) throws Exception {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setText(dto.getText());
        BloodBank bb = bloodBankRepository.findByEmail(dto.getBankEmail()).get();
        news.setBloodBank(bb);
        dto.setBankApi(bb.getAPIKey());
        rabbitMQSender.sendNews(dto);
        return Create(news);
    }
}
