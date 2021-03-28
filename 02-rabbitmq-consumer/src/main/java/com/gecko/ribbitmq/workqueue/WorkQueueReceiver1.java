package com.gecko.ribbitmq.workqueue;


import com.gecko.rabbitmq.util.RabbitMQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 默认是平均分配，分配在执行前完成
 * 平均分配存在的问题：
 *     若一个消费者消费执行效率慢另一个消费者效率较高时
 *     会产生消息堆积，影响整体业务执行速度
 *   通过使用channel.basicQos(1);保证每次只能消费一个消息
 *   关闭消息自动确认，使哪个执行效率高则多消费
 *   实现能者多劳模式，提高效率
 */
public class WorkQueueReceiver1 {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQUtil.getChannel();
        channel.basicQos(1); // 每一次只能消费一个消息
        // 参数1：队列名称，参数2：消息自动确认
        // autoAck:true当默认平均分配完，消费者自动向RabbitMQ确认消息已消费
        // 此机制开启，它不关心消息是否处理完，只要拿到平均分配的消息，就告诉队列消息已处理完
        // 问题：假设分配到5条消息，执行较慢或者是执行到一半宕机或崩溃，会导致消息丢失
        // 官方文档中不建议使用自动确认，一定避免消息丢失的情况，保证业务数据的完整性
        channel.basicConsume("workQueue", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("workQueue Msg One: " + new String(body, StandardCharsets.UTF_8));
                // 关闭自动确认后，需要手动确认消息
                // 参数1：确认队列中的哪个具体消息， 参数2：是否开启多个消息同时确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
