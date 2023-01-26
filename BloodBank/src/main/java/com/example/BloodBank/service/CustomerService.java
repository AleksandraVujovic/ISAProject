package com.example.BloodBank.service;

import com.example.BloodBank.exceptions.EmailTakenException;
import com.example.BloodBank.exceptions.UsernameTakenException;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.Customer;
import com.example.BloodBank.model.User;
import com.example.BloodBank.service.service_interface.repository.CustomerRepository;
import com.example.BloodBank.service.service_interface.repository.UserRepository;
import com.example.BloodBank.service.service_interface.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository){
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }
    public void registerCustomer(Customer customer) throws Exception {

        try {
            for (Customer customerIt : customerRepository.findAll()){
                if (customerIt.getUsername().equals(customer.getUsername())){
                    System.out.println("Username: " + customer.getUsername() +
                            " is taken!");
                    throw new UsernameTakenException(customer.getUsername());
                }
                if (customerIt.getEmail().equals(customer.getEmail())){
                    System.out.println("Email: " + customer.getEmail() +
                            " is taken!");
                    throw new EmailTakenException(customer.getEmail());
                }
            }
            Create(customer);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<Customer> getAll() throws Exception {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Customer FindByUsername(String username) {
        return customerRepository.findByUsername(username).get();
    }

    @Override
    public Customer Create(Customer entity) throws Exception {
        return customerRepository.save(entity);
    }

    @Override
    public Customer Read(Long id) throws Exception {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            return customer.get();
        } else {
            throw new EntityDoesntExistException(id);
        }
    }
    @Override
    public List<Customer> findAllByFirstNameOrLastName(String searchTerm, Pageable page){
        Page<User> pageUsers =  userRepository.findAllCustomersByFirstNameOrLastName(searchTerm.toLowerCase(), page);
         List<Customer> returnList = new ArrayList<>();
        for(User u : pageUsers){
            returnList.add(customerRepository.findById(u.getId()).get());
        }
        return returnList;
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, allEntries = true, key = "#entity.getUsername()")
    public Customer Update(Customer entity) throws Exception {
        return customerRepository.save(entity);
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, allEntries = true, key = "#entity.getUsername()")
    public void Delete(Customer entity) throws Exception {
        customerRepository.delete(entity);
    }

    @Override
    public Iterable<Customer> GetAll() throws Exception {
        return customerRepository.findAll();
    }

    @Override
    public int getCustomersAmountWithSearch(String search) {
        List<User> users = userRepository.findAllCustomersBySearch(search.toLowerCase());
        return users.size();
    }



    public Optional<Customer> findByEmail(String email) {
        if(!customerRepository.findByEmail(email).isPresent())
            throw new IllegalStateException("Customer with that kind of email doesn't exist!");
        return customerRepository.findByEmail(email);
    }
}
