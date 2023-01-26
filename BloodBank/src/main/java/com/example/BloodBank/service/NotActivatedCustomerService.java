package com.example.BloodBank.service;

import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.*;
import com.example.BloodBank.repository.CustomerRepository;
import com.example.BloodBank.repository.NotActivatedCustomerRepository;
import com.example.BloodBank.repository.UserRepository;
import com.example.BloodBank.service.service_interface.INotActivatedCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotActivatedCustomerService implements INotActivatedCustomerService {
    private final NotActivatedCustomerRepository notActivatedCustomerRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    public NotActivatedCustomerService(NotActivatedCustomerRepository notActivatedCustomerRepository,
                                       UserRepository userRepository,
                                       CustomerRepository customerRepository){
        this.notActivatedCustomerRepository = notActivatedCustomerRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }
    @Override
    public NotActivatedCustomer Create(NotActivatedCustomer entity) throws Exception {
        //check if username or email are taken by some user or notActivatedUser
        String uuid = UUID.randomUUID().toString();
        entity.setRole(Role.CUSTOMER);
        emailSenderService.sendSimpleEmail(entity.getEmail(), "Blood bank activation", "http://localhost:8086/activate/"+uuid);
        entity.setActivation(uuid);
        return notActivatedCustomerRepository.save(entity);
    }

    @Override
    public NotActivatedCustomer Read(Long id) throws Exception {
        Optional<NotActivatedCustomer> user = notActivatedCustomerRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new EntityDoesntExistException();
        }
    }

    @Override
    public NotActivatedCustomer Update(NotActivatedCustomer entity) throws Exception {
        return null;
    }

    @Override
    public void Delete(NotActivatedCustomer entity) throws Exception {
        notActivatedCustomerRepository.delete(entity);
    }

    @Override
    public Iterable<NotActivatedCustomer> GetAll() throws Exception {
        return notActivatedCustomerRepository.findAll();
    }
    public NotActivatedCustomer ReadByActivationCode(String code) throws Exception {
        Optional<NotActivatedCustomer> user = notActivatedCustomerRepository.findByActivation(code);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new EntityDoesntExistException();
        }
    }
    public Boolean Activate(String activationCode) throws Exception{
        NotActivatedCustomer notActivatedCustomer = ReadByActivationCode(activationCode);
        Customer newCustomer = makeCustomerFromNotActivated(notActivatedCustomer);
        customerRepository.save(newCustomer);
        //delete not activated from base
        return true;
    }
    private Address createAddressFromNotActivated(NotActivatedCustomer user){
        Address add = new Address();
        if (user.getAddress() != null){
            add.setCountry(user.getAddress().getCountry());
            add.setCity(user.getAddress().getCity());
            add.setStreet(user.getAddress().getStreet());
            add.setNumber(user.getAddress().getNumber());
        }
        return add;
    }
    private Customer makeCustomerFromNotActivated(NotActivatedCustomer notActivatedCustomer){
        Address add = createAddressFromNotActivated(notActivatedCustomer);

        Customer newCustomer = new Customer();
        newCustomer.setUsername(notActivatedCustomer.getUsername());
        newCustomer.setPassword(notActivatedCustomer.getPassword());
        newCustomer.setEmail(notActivatedCustomer.getEmail());
        newCustomer.setAddress(add);
        newCustomer.setDob(notActivatedCustomer.getDob());
        newCustomer.setGender(notActivatedCustomer.getGender());
        newCustomer.setRole(notActivatedCustomer.getRole());
        newCustomer.setFirstName(notActivatedCustomer.getFirstName());
        newCustomer.setLastName(notActivatedCustomer.getLastName());
        newCustomer.setJmbg(notActivatedCustomer.getJmbg());
        newCustomer.setPhoneNumber(notActivatedCustomer.getPhoneNumber());
        newCustomer.setProfession(notActivatedCustomer.getProfession());
        newCustomer.setProfessionInfo(notActivatedCustomer.getProfessionInfo());
        return  newCustomer;
    }
}
