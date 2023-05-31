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
    private String queueDelayName;

    /**
     * 创建自定义交换机
     * @return
     */
    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");    // 自定义交换机类型   x-delayed-type  延迟类型 必须是direct

        //自定义延迟交换机
        CustomExchange customExchange = new CustomExchange(exchangeName, "x-delayed-message", true, false, arguments);


        return customExchange;
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueDelayName)
                .build();
    }


    /**
     * 绑定
     * @param customExchange
     * @param queue
     * @return
     */
    @Bean
    public Binding binding(CustomExchange customExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(customExchange).with("plugins").noargs();
    }


}
