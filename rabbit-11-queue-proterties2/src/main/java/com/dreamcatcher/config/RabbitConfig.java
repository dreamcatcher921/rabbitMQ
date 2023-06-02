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
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName) //交换机名称
                .build();
    }

    /**
     * 队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        //Queue(String name, boolean durable, boolean exclusive, boolean autoDelete,@Nullable Map<String, Object> arguments)
        Map<String, Object> arguments = new HashMap<>();
        //拒绝发布
        arguments.put("x-overflow", "reject-publish");  //队列的溢出行为，默认删除头部消息，改成拒绝发布
        //设置队列的最大长度
        arguments.put("x-max-length", 5);
        //单一消费者 队列如果创建了，就不允许修改，只能删除重建
        //arguments.put("x-single-active-consumer", true);
        //设置消息容量
        //arguments.put("x-max-length-bytes", 1024);
        //设置队列的最大优先级
        //arguments.put("x-max-priority", 10);
        //autoDelete 自动删除
        return new Queue(queueName, true, false, false, arguments);
    }

    /**
     * 绑定 交换机和队列
     * with("info");  指定路由键
     *
     * @param directExchange
     * @param queue
     * @return
     */
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }

}
