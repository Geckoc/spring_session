package com.gecko.rabbitmq.waitforconfirm;

import com.gecko.rabbitmq.config.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *  异步监听发送方确认模式
 */
public class AddConfirmListener {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQUtil.getChannel();
        channel.queueDeclare("addConfirmListener", true, false, false, null);
        // 开启发送方确认模式
        channel.confirmSelect();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            channel.basicPublish("", "addConfirmListener", null, "test".getBytes(StandardCharsets.UTF_8));
        }
        channel.addConfirmListener(new ConfirmListener() {
            // 消息发送成功后的回调方法
            // 参数1： 消息的标识，参数2：当前消息是否属于批量处理
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("异步监听消息确认模式： " + deliveryTag + ":::" + multiple);
            }
            // 消息发送失败后的回调方法
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("异步监听消息确认模式： " + deliveryTag + ":::" + multiple);
            }
        });

        System.out.println(System.currentTimeMillis() - start); // 计算执行时间
        RabbitMQUtil.releaseRabbitMQ();
        System.out.println("消息发送成功");
    }
}
