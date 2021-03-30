package com.gecko.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectReceiver {
    //@RabbitListener
    // 注解用于标记当前方法为消息监听方法，可以监听某个队列
    // 当队列中有新消息则自动完成接收，
    @RabbitListener(queues = "directQueueBoot")
    public void direct(String msg) {
        System.out.println("direct message: " + msg);
    }
}
