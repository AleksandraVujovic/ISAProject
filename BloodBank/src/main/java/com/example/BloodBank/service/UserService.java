package com.example.BloodBank.service;

import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.User;
import com.example.BloodBank.service.service_interface.repository.UserRepository;
import com.example.BloodBank.service.service_interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User Create(User entity) throws Exception {
        return userRepository.save(entity);
    }

    @Override
    public User Read(Long id) throws Exception {
        Optional<User> admin = userRepository.findById(id);
        if(admin.isPresent()){
            return admin.get();
        } else {
            throw new EntityDoesntExistException(id);
        }
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, allEntries = true, key = "#entity.getUsername()")
    public User Update(User entity) throws Exception {
        Optional<User> user = userRepository.findById(entity.getId());
        if(user.isPresent()){
            User temp = user.get();
            temp.updateUserInfo(entity);
            return userRepository.save(temp);
        } else {
            throw new EntityDoesntExistException(entity.getId());
        }
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, allEntries = true, key = "#entity.getUsername()")
    public void Delete(User entity) throws Exception {
        userRepository.delete(entity);
    }

    @Override
    public Iterable<User> GetAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByFirstNameOrLastName(String searchTerm, Pageable page) {
        Page<User> pageUsers =  userRepository.findAllByFirstNameOrLastName(searchTerm.toLowerCase(), page);
        List<User> users = new ArrayList<>();
        for (User u : pageUsers) {
            users.add(u);
        }
        return users;
    }

    @Override
    public int getUsersAmountWithSearch(String search) {
        List<User> users = userRepository.findAllBySearch(search.toLowerCase());
        return users.size();
    }
}
