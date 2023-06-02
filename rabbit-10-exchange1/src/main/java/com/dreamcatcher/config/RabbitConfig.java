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
    private String queueName;

    /**
     * 交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName) //交换机名称
                .durable(true) //默认true       false不持久化  rabbitmq重启后交换机消失
                .autoDelete() //设置为自动删除  当没有队列或者交换机绑定到当前交换机时，该交换机会自动删除 默认false

                .build();
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
