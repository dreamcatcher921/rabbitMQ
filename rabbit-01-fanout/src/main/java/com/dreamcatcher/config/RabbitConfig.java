package com.dreamcatcher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
     //定义交换机   Exchange  交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("exchange.fanout");
    }
    //定义队列
    @Bean
    public Queue queueA(){
        //参数：队列名称，是否持久化  构造方法
        return new Queue("queue.fanout.a");
    }
    @Bean
    public Queue queueB(){
        return new Queue("queue.fanout.b");
    }
    //绑定交换机和队列
    @Bean
    public Binding bindingA(FanoutExchange fanoutExchange, Queue queueA){
        //queueA 是方法名，依赖注入
        //将队列A绑定到扇形交换机上
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }

    @Bean
    public Binding bindingB(FanoutExchange fanoutExchange, Queue queueB){
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
}
