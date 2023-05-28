package com.dreamcatcher.config;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue queue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 35000);    // 设置队列的过期时间 35s 毫秒数
        //return new  Queue(queueName,true,false,false,arguments);



        return QueueBuilder.durable(queueName).withArguments(arguments).build();
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
