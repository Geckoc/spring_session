package com.gecko.rabbitmq.transaction;

import com.gecko.rabbitmq.config.RabbitMQUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * 事务的机制，确保每一条消息的发送成功
 * 在消息发送前开启事务
 * 发送消息后，若事务未提交，则只存在队列而消息不存在
 */
public class SendTransactionalMessage {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtil.getChannel();
        try {
            // 声明交换器
            channel.exchangeDeclare("transactionExchange", "direct" , true);
            // 声明队列
            channel.queueDeclare("transactionQueue", true, false, true, null);
            // 绑定交换器与队列关系
            channel.queueBind("transactionQueue", "transactionExchange", "transaction");
            // 开启事务
            channel.txSelect();
            // 发送消息
            String message = "transaction Message";
            channel.basicPublish("transactionExchange", "transaction", null, message.getBytes());
            // 提交事务
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚
            try {
                channel.txRollback();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            RabbitMQUtil.releaseRabbitMQ();
        }
    }
}
