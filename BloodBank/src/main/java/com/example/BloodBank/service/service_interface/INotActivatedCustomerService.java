package com.example.BloodBank.service.service_interface;

import com.example.BloodBank.model.NotActivatedCustomer;

public interface INotActivatedCustomerService extends  ICRUDService<NotActivatedCustomer> {
    public NotActivatedCustomer ReadByActivationCode(String code) throws Exception;
    public Boolean Activate(String activationCode) throws Exception;
}
