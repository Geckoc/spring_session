package com.gecko.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectReceiver {

    @RabbitListener(queues = "directQueueBoot")
    public void direct(String msg) {
        System.out.println("direct message: " + msg);
    }
}
