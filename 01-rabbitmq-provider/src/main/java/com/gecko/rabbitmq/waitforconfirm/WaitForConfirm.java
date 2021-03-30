package com.gecko.rabbitmq.waitforconfirm;

import com.gecko.rabbitmq.config.RabbitMQUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *  普通发送方确认模式
 * 	Confirm发送方确认模式使用和事务类似
 * 	也是通过设置Channel进行发送方确认的，最终达到确保所有的消息全部发送成功
 */
public class WaitForConfirm {
    public static void main(String[] args) throws IOException, InterruptedException {
        Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare("wfcExchange", "direct");
        channel.queueDeclare("wfcQueue", true, false, false, null);
        channel.queueBind("wfcQueue", "wfcExchange", "wfcKey");
        long start = System.currentTimeMillis();
        // 开启发送方确认模式
        channel.confirmSelect();
        for (int i = 0; i < 500; i++) {
            channel.basicPublish("wfcExchange", "wfcKey", null, "test".getBytes(StandardCharsets.UTF_8));
        }
        // 当前方法是阻塞线程的，等待发送的结果回来，再向下执行
        boolean flag = channel.waitForConfirms();
        if (!flag){
            System.out.println("重新发送....");
        }
        System.out.println(System.currentTimeMillis() - start); // 计算执行时间
        System.out.println("消息发送成功");
        RabbitMQUtil.releaseRabbitMQ();

    }
}
