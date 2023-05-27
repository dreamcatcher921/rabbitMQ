package com.dreamcatcher.config;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Setter
@Configuration
@ConfigurationProperties(prefix = "my") //读取配置文件中的my开头的配置
public class RabbitConfig {

    private String exchangeName;
    private String queueAName;
    private String queueBName;


    //定义交换机   Exchange  交换机
    @Bean
    public DirectExchange directExchange(){
        //使用建造者模式创建交换机
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    //定义队列
    @Bean
    public Queue QueueA(){
        return QueueBuilder.durable(queueAName).build();
    }
    @Bean
    public Queue QueueB(){
        return QueueBuilder.durable(queueBName).build();
    }
    //绑定交换机和队列
    @Bean
    public Binding bindingA(DirectExchange directExchange,Queue QueueA){
        return BindingBuilder.bind(QueueA).to(directExchange).with("error");
    }
    @Bean
    public Binding bindingB(DirectExchange directExchange,Queue QueueB){
        return BindingBuilder.bind(QueueB).to(directExchange).with("info");
    }
    @Bean
    public Binding bindingB2(DirectExchange directExchange,Queue QueueB){
        return BindingBuilder.bind(QueueB).to(directExchange).with("error");
    }
    @Bean
    public Binding bindingB3(DirectExchange directExchange,Queue QueueB){
        return BindingBuilder.bind(QueueB).to(directExchange).with("warning");
    }

}
