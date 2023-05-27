package com.dreamcatcher.config;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@ConfigurationProperties(prefix = "my")
public class RabbitConfig {
    private String exchangeName;
    private String queueAName;
    private String queueBName;

    @Bean
    public TopicExchange topicExchange(){
        return ExchangeBuilder.topicExchange(exchangeName).build();
    }

    @Bean
    public Queue queueA(){
        return QueueBuilder.durable(queueAName).build();
    }


    @Bean
    public Queue queueB(){
        return QueueBuilder.durable(queueBName).build();
    }

    @Bean
    public Binding bindingA(TopicExchange topicExchange,Queue queueA){
        return BindingBuilder.bind(queueA).to(topicExchange).with("*.orange.*");
    }

    @Bean
    public Binding bindingB(TopicExchange topicExchange,Queue queueB){
        return BindingBuilder.bind(queueB).to(topicExchange).with("*.*.rabbit");
    }

    @Bean
    public Binding bindingB2(TopicExchange topicExchange,Queue queueB){
        return BindingBuilder.bind(queueB).to(topicExchange).with("lazy.#");
    }
}
