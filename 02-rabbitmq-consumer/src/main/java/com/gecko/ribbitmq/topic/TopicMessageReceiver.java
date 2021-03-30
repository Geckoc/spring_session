package com.gecko.ribbitmq.topic;

import com.gecko.rabbitmq.config.RabbitMQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * 	注意：
 *
 * 1、Topic模式的消息接收时必须要指定RoutingKey并且可以使用# 和 *来做统配符号，*表示通配任意一个单词 #表示通配任意多个单词，
 *   例如消费者的RoutingKey为test.#或#.myRoutingKey都可以获取RoutingKey为test.myRoutingKey发送者发送的消息
 */
public class TopicMessageReceiver {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQUtil.getChannel();

        channel.basicConsume("topicQueue3", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("TopicMessageContent: " + new String(body));
            }
        });
    }
}
