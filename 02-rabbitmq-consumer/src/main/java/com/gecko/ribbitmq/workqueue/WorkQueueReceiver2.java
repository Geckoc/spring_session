package com.gecko.ribbitmq.workqueue;


import com.gecko.rabbitmq.config.RabbitMQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WorkQueueReceiver2 {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQUtil.getChannel();
        channel.basicQos(1); // 每一次只能消费一个消息
        channel.basicConsume("workQueue", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("workQueue Msg Two: " + new String(body, StandardCharsets.UTF_8));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
