package com.gecko.rabbitmq;

import com.gecko.rabbitmq.emitmessage.EmitDirectMessage;
import com.gecko.rabbitmq.emitmessage.EmitFanoutMessage;
import com.gecko.rabbitmq.emitmessage.EmitTopicMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.annotation.security.RunAs;

@SpringBootTest(classes = Application.class)
class ApplicationTests {

    @Resource
    private EmitDirectMessage emitDirectMessage;

    @Resource
    private EmitFanoutMessage emitFanoutMessage;

    @Resource
    private EmitTopicMessage emitTopicMessage;

    @Test
    void directMessage() {
        emitDirectMessage.emit();
    }

    @Test
    void fanoutMessage() {
        emitFanoutMessage.emit();
    }

    @Test
    void topicMessage() {
        emitTopicMessage.emitAA();
        emitTopicMessage.emitAABB();
        emitTopicMessage.emitAABBCC();
    }

}
