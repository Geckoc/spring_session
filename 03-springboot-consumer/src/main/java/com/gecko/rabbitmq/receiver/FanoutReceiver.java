package com.gecko.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *  fanoutExchange 一对多 ：一个生产者  多个消费者
 */
@Component
public class FanoutReceiver {
    /**
     * queues : 监听的队列名称
     * bindings : 声明绑定关系
     * @Queue() =  * @return the queue name or "" for a generated queue name (default).
     * 声明随机消息队列名称
     * @param msg 消息内容
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(), exchange = @Exchange(name = "fanoutExchangeBoot", type = "fanout")))
    public void fanout1(String msg) {
        System.out.println("fanoutReceiver1: " + msg);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(), exchange = @Exchange(name = "fanoutExchangeBoot", type = "fanout")))
    public void fanout2(String msg) {
        System.out.println("fanoutReceiver2: " + msg);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(), exchange = @Exchange(name = "fanoutExchangeBoot", type = "fanout")))
    public void fanout3(String msg) {
        System.out.println("fanoutReceiver3: " + msg);
    }
}
