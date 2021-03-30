package com.gecko.rabbitmq.emitmessage;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EmitFanoutMessage {
    @Resource
    private AmqpTemplate amqpTemplate;

    public void emit(){
        amqpTemplate.convertAndSend("fanoutExchangeBoot", "", "Hello Fanout");
    }
}
