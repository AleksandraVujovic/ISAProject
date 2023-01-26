package com.example.BloodBank.service;

import com.example.BloodBank.dto.FilledOrderDTO;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.ScheduledOrder;
import com.example.BloodBank.service.service_interface.repository.ScheduledOrdersRepository;
import com.example.BloodBank.service.service_interface.IScheduledOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledOrderService implements IScheduledOrderService {

    private final ScheduledOrdersRepository scheduledOrdersRepository;
    private final BloodBankService bloodBankService;
    private final RabbitMQSender rabbitMQSender;


    @Autowired
    public ScheduledOrderService(ScheduledOrdersRepository scheduledOrdersRepository,
                                 BloodBankService bloodBankService,
                                 RabbitMQSender rabbitMQSender){
        this.scheduledOrdersRepository = scheduledOrdersRepository;
        this.bloodBankService = bloodBankService;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    public ScheduledOrder Create(ScheduledOrder entity) throws Exception {
        return scheduledOrdersRepository.save(entity);
    }

    @Override
    public ScheduledOrder Read(Long id) throws Exception {
        Optional<ScheduledOrder> scheduledOrder = scheduledOrdersRepository.findById(id);
        if(scheduledOrder.isPresent()){
            return scheduledOrder.get();
        } else {
            throw new EntityDoesntExistException(id);
        }
    }

    @Override
    public ScheduledOrder Update(ScheduledOrder entity) throws Exception {
        return scheduledOrdersRepository.save(entity);
    }

    @Override
    public void Delete(ScheduledOrder entity) throws Exception {
        scheduledOrdersRepository.delete(entity);
    }

    @Override
    public List<ScheduledOrder> GetAll() throws Exception {
        return scheduledOrdersRepository.findAll();
    }

    @Override
    public void sendOrders() throws Exception{
        List<ScheduledOrder> scheduledOrderList = GetAll();
        for (ScheduledOrder so : scheduledOrderList){
            if(checkIfNDaysBefore(so, 5)){
                SendMessageIfCantSendBlood(so);
                break;
            }
            if (!checkIfCorrectDay(so)) break;
            bloodBankService.reduceBloodSupply(so.getBankEmail(), so);
            FilledOrderDTO fo = SetupFilledOrder(so);
            rabbitMQSender.sendOrder(fo);
        }
    }

    private void SendMessageIfCantSendBlood(ScheduledOrder so) {
        if(!bloodBankService.checkIfBloodSupplyAvailable(so.getBankEmail(), so)){
            SendFailedBloodDelivery(so);
        }
    }


    private void SendFailedBloodDelivery(ScheduledOrder so) {
        FilledOrderDTO fo = SetupFilledOrder(so);
        fo.setSent(false);
        rabbitMQSender.sendOrder(fo);
    }

    private FilledOrderDTO SetupFilledOrder(ScheduledOrder so) {
        FilledOrderDTO fo = new FilledOrderDTO();
        fo.readScheduled(so);
        fo.setBankApi(bloodBankService.findByEmail(so.getBankEmail()).get().getAPIKey());
        fo.setSent(true);
        return fo;
    }

    private static boolean checkIfCorrectDay(ScheduledOrder so) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getDate() == so.getDayOfMonth();
    }

    private static boolean checkIfNDaysBefore(ScheduledOrder so, int nDays) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (timestamp.getDate() - nDays) == so.getDayOfMonth();
    }
}
