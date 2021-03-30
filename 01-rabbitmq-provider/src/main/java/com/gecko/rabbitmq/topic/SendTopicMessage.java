package com.gecko.rabbitmq.topic;

import com.gecko.rabbitmq.config.RabbitMQUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 *  topic是属于路由模式的衍生
 *  动态路由模式 可使用通配符设置路由规则，更加灵活
 * 	注意：
 * 1、在topic模式中必须要指定RoutingKey，并且可以同时指定多层的RoutingKey，
 *   每个层次之间使用 点分隔即可 例如test.myRoutingKey
 */
public class SendTopicMessage {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQUtil.getChannel();
        // 声明交换器
        channel.exchangeDeclare("topicExchange", "topic", true);
        // 声明队列
        channel.queueDeclare("topicQueue1", true, false, false, null);
        channel.queueDeclare("topicQueue2", true, false, false, null);
        channel.queueDeclare("topicQueue3", true, false, false, null);
        // 绑定关系
        channel.queueBind("topicQueue1", "topicExchange", "aa.*");
        channel.queueBind("topicQueue2", "topicExchange", "aa.#");
        channel.queueBind("topicQueue3", "topicExchange", "aa");
        // 发送消息
//        channel.basicPublish("topicExchange", "aa", null, "aa".getBytes());
//        channel.basicPublish("topicExchange", "aa.bb", null, "aa.bb".getBytes());
        channel.basicPublish("topicExchange", "aa.bb.cc", null, "aa.bb.cc".getBytes());
        System.out.println("TopicMessage Send Successful !");
        RabbitMQUtil.releaseRabbitMQ();
    }
}
