package com.gecko.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // 声明关系 交换机 队列 绑定
    @Bean
    public DirectExchange getDirectExchange() {
        return new DirectExchange("directExchange");
    }
    @Bean
    public FanoutExchange getFanoutExchange() {
        return new FanoutExchange("fanoutExchangeBoot");
    }
    @Bean
    public TopicExchange getTopicExchange() {
        return new TopicExchange("topicExchangeBoot");
    }
    // --- declare multi topic Queue ---
    @Bean
    public Queue topicQueueA() {
        return new Queue("topicQueueA");
    }
    @Bean
    public Queue topicQueueB() {
        return new Queue("topicQueueB");
    }
    @Bean
    public Queue topicQueueC() {
        return new Queue("topicQueueC");
    }
    // --- declare multi topic Queue ---

    // --- declare multi binding for topic ---
    @Bean
    public Binding directBindingA(Queue topicQueueA, TopicExchange getTopicExchange) {
        return BindingBuilder.bind(topicQueueA).to(getTopicExchange).with("aa");
    }
    @Bean
    public Binding directBindingB(Queue topicQueueB, TopicExchange getTopicExchange) {
        return BindingBuilder.bind(topicQueueB).to(getTopicExchange).with("aa.*");
    }
    @Bean
    public Binding directBindingC(Queue topicQueueC, TopicExchange getTopicExchange) {
        return BindingBuilder.bind(topicQueueC).to(getTopicExchange).with("aa.#");
    }
    // --- declare multi binding for topic ---

    @Bean
    public Queue getQueue() {
        return new Queue("directQueueBoot");
    }

    @Bean
    public Binding directBinding(Queue getQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(getQueue).to(directExchange).with("directRoutingKey");
    }

}
