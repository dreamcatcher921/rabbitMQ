package com.dreamcatcher.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@ConfigurationProperties(prefix = "my")
@Slf4j
public class RabbitConfig  {

    private String exchangeName;
    private String queueName;

    /**
     * 交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueName).build();
    }

    /**
     * 绑定 交换机和队列
     * with("info");  指定路由键
     * @param directExchange
     * @param queue
     * @return
     */
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }

}
