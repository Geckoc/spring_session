package com.gecko.rabbitmq.emitmessage;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  dynamic routingKeyMode topicExchange
 *  config many routingRule
 */
@Component
public class EmitTopicMessage {
    @Resource
    private AmqpTemplate amqpTemplate;
    private String message = "topic Message in Springboot";
    public void emitAA() {
        amqpTemplate.convertAndSend("topicExchangeBoot", "aa", message);
    }
    public void emitAABB() {
        amqpTemplate.convertAndSend("topicExchangeBoot", "aa.bb", message);
    }
    public void emitAABBCC() {
        amqpTemplate.convertAndSend("topicExchangeBoot", "aa.bb.cc", message);
    }
}
