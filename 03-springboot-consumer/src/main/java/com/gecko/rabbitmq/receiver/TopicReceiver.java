package com.gecko.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 动态路由模式，
 */
@Component
public class TopicReceiver {

    @RabbitListener(queues = "topicQueueA")
    public void topicA(String msg) {
        System.out.println("topicMessage A: " + msg);
    }

    @RabbitListener(queues = "topicQueueB")
    public void topicB(String msg) {
        System.out.println("topicMessage B: " + msg);
    }

    @RabbitListener(queues = "topicQueueC")
    public void topicC(String msg) {
        System.out.println("topicMessage C: " + msg);
    }

}
