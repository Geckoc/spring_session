package com.gecko.rabbitmq.waitforconfirm;

import com.gecko.rabbitmq.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *  批量确认模式
 * 	Confirm发送方确认模式使用和事务类似
 * 	也是通过设置Channel进行发送方确认的，最终达到确保所有的消息全部发送成功
 */
public class WaitForConfirmOrDie {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQUtil.getChannel();
        channel.queueDeclare("wfcODQueue", true, false, false, null);
        long start = System.currentTimeMillis();
        // 开启发送方确认模式
        channel.confirmSelect();
        for (int i = 0; i < 500; i++) {
            channel.basicPublish("", "wfcODQueue", null, "test".getBytes(StandardCharsets.UTF_8));
        }
        // 当前方法是阻塞线程的，等待发送的结果回来，再向下执行
        try {
            channel.waitForConfirmsOrDie();
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 发送失败重新发送消息
        } finally {
            RabbitMQUtil.releaseRabbitMQ();
        }
        System.out.println(System.currentTimeMillis() - start); // 计算执行时间
        System.out.println("消息发送成功");

    }
}
