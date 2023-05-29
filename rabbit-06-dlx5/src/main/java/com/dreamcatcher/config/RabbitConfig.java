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

    private String exchangeNormalName;
    private String queueNormalName;
    private String exchangeDlxName;
    private String queueDlxName;

    /**
     * 正常交换机
     *
     * @return
     */
    @Bean
    public DirectExchange exchangeNormal() {
        //构造方法
        return new DirectExchange(exchangeNormalName);
    }

    /**
     * 正常队列
     *
     * @return
     */
    @Bean
    public Queue queueNormal() {
        Map<String, Object> arguments = new HashMap<>();
        ////设置过期时间15s
        //arguments.put("x-message-ttl", 15000);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", exchangeDlxName);
        //设置死信路由键  死信交换机会根据这个路由键去找死信队列 死信队列的路由键也是这个 如果不一样就会直接丢弃
        arguments.put("x-dead-letter-routing-key", "error");
        //构造方法
        return new Queue(queueNormalName, true, false, false, arguments);
    }

    @Bean
    public Binding bindingNormal(DirectExchange exchangeNormal, Queue queueNormal) {
        //建造者方法 with()就是路由键
        return BindingBuilder.bind(queueNormal).to(exchangeNormal).with("order");
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange exchangeDlx() {
        //建造者方法
        return ExchangeBuilder.directExchange(exchangeDlxName).build();
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue queueDlx() {
        //建造者方法
        return QueueBuilder.durable(queueDlxName)

                .build();
    }


    @Bean
    public Binding bindingDlx(DirectExchange exchangeDlx, Queue queueDlx) {
        //建造者方法 with()就是路由键
        return BindingBuilder.bind(queueDlx).to(exchangeDlx).with("error");
    }


}
