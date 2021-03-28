package com.gecko.rabbitmq.workqueue;


import com.gecko.rabbitmq.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 *  工作模型方式发送消息
 *  10条消息，两个消费者，默认是平均分配，分配在执行前完成
 */
public class EmitWorkQueue {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQUtil.getChannel();
        channel.queueDeclare("workQueue", true, false, false, null);
        String message = "workQueue Message";
        // 发送多个消息
        for (int i = 0; i < 20; i++) {
            // MessageProperties.PERSISTENT_TEXT_PLAIN 该属性设置消息持久化
            channel.basicPublish("", "workQueue", MessageProperties.PERSISTENT_TEXT_PLAIN, (i+message).getBytes());
        }
        // 关闭资源
        RabbitMQUtil.releaseRabbitMQ();
    }
}
