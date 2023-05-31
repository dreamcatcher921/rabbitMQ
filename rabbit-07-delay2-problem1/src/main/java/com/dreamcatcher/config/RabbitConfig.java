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
    private String queueNormalName;
    private String queueDlxName;

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
    public Queue queueNormal() {
        //Map<String, Object> arguments = new HashMap<>();
        //arguments.put("x-message-ttl", 25000);    // 设置队列的过期时间 25s 毫秒数
        //return new  Queue(queueName,true,false,false,arguments);



        return QueueBuilder.durable(queueNormalName)
                //.ttl(25000) // 设置队列的过期时间 25s 毫秒数
                //设置单条消息的时间 在发送消息的时候设置
                .deadLetterExchange(exchangeName)  // 设置死信交换机 ,设置相同的交换机，可以实现延迟队列
                .deadLetterRoutingKey("error")  // 设置死信路由键，设置交换机和死信队列绑定的key
                .build();
    }


    /**
     * 绑定 交换机和队列
     * with("info");  指定路由键
     * @param directExchange
     * @param queueNormal
     * @return
     */
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queueNormal) {
        return BindingBuilder.bind(queueNormal).to(directExchange).with("order");
    }

    @Bean
    public Queue queueDlx() {
        return QueueBuilder.durable(queueDlxName).build();
    }

    @Bean
    public Binding bindingDlx(DirectExchange directExchange, Queue queueDlx) {
        return BindingBuilder.bind(queueDlx).to(directExchange).with("error");
    }
}
