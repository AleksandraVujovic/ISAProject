package com.example.BloodBank;


import com.example.BloodBank.service.ScheduledOrderService;
import com.example.BloodBank.service.service_interface.IScheduledOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.BloodBank.service.RabbitMQSender;

@Component
public class ScheduledTasks {

    private final RabbitMQSender rabbitMQSender;

    private final IScheduledOrderService scheduledOrderService;

    @Autowired
    public ScheduledTasks(RabbitMQSender rabbitMQSender, IScheduledOrderService scheduledOrderService) {
        this.rabbitMQSender = rabbitMQSender;
        this.scheduledOrderService = scheduledOrderService;
    }

    @Scheduled(cron = "0 0 8 * * ?", zone = "Europe/Berlin")
    //@Scheduled(fixedRate = 3000)
    public void sendMessage() {
        System.out.println("RabbitMQ sent message");
        try {
            scheduledOrderService.sendOrders();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
