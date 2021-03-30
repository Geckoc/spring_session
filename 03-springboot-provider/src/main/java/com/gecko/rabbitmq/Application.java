package com.gecko.rabbitmq;

import com.gecko.rabbitmq.emitmessage.EmitDirectMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        EmitDirectMessage emitDirectMessage = run.getBean(EmitDirectMessage.class);
        emitDirectMessage.emit();
    }

}
