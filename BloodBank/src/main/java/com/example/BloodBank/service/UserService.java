package com.example.BloodBank.service;

import com.example.BloodBank.excpetions.EntityDoesntExistException;
import com.example.BloodBank.model.User;
import com.example.BloodBank.repository.UserRepository;
import com.example.BloodBank.service.service_interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public void Delete(User entity) throws Exception {
        userRepository.delete(entity);
    }

    @Override
    public Iterable<User> GetAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllByFirstNameOrLastName(String searchTerm, Pageable page) {
        return userRepository.findAllByFirstNameOrLastName(searchTerm, page);
    }
}
