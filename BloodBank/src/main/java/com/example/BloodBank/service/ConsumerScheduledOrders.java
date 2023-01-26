package com.example.BloodBank.service;

import com.example.BloodBank.model.ScheduledOrder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConsumerScheduledOrders {
    private final ScheduledOrderService scheduledOrderService;

    @Autowired
    public ConsumerScheduledOrders(ScheduledOrderService scheduledOrderService){
        this.scheduledOrderService = scheduledOrderService;
    }

    @RabbitListener(queues = "${custom.rabbitmq.scheduledOrdersQueue}")
    public void handler(String message){
        try{
            System.out.println("GOT MESSAGE!");
            System.out.println(message);
            String newMessage = message;
            Map<String, Object> scheduleData = new Gson().fromJson(message, new TypeToken<HashMap<String, Object>>() {
            }.getType());
            ScheduledOrder so = new ScheduledOrder();
            so.setDayOfMonth(((Double) scheduleData.get("DayOfMonth")).intValue());
            so.setAplus(((Double) scheduleData.get("APlus")).intValue());
            so.setBplus(((Double) scheduleData.get("BPlus")).intValue());
            so.setABplus(((Double) scheduleData.get("ABPlus")).intValue());
            so.setOplus(((Double) scheduleData.get("OPlus")).intValue());

            so.setAminus(((Double) scheduleData.get("AMinus")).intValue());
            so.setBminus(((Double) scheduleData.get("BMinus")).intValue());
            so.setABminus(((Double) scheduleData.get("ABMinus")).intValue());
            so.setOminus(((Double) scheduleData.get("OMinus")).intValue());

            so.setBankEmail((String) scheduleData.get("BankEmail"));
            so.setHospitalEmail((String) scheduleData.get("HospitalEmail"));
            scheduledOrderService.Create(so);
            System.out.println(so.toString());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
