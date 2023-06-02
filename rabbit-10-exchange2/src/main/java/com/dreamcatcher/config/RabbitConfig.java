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

    private String exchangeNormalName;
    private String exchangeAlternateName;
    private String queueNormalName;
    private String queueAlternateName;

    /**
     * 交换机
     * @return
     */
    @Bean
    public DirectExchange normalExchange() {
        return ExchangeBuilder.directExchange(exchangeNormalName) //交换机名称
                .alternate(exchangeAlternateName) //备用交换机
                .build();
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue queueNormal() {
        return QueueBuilder.durable(queueNormalName).build();
    }

    /**
     * 绑定 交换机和队列
     * with("info");  指定路由键
     * @param normalExchange
     * @param queueNormal
     * @return
     */
    @Bean
    public Binding binding(DirectExchange normalExchange, Queue queueNormal) {
        return BindingBuilder.bind(queueNormal).to(normalExchange).with("info");
    }

    /**
     * 备用交换机 一般设置为扇形交换机 不用设置路由键
     * @return
     */
    @Bean
    public FanoutExchange alternateExchange() {
        return ExchangeBuilder.fanoutExchange(exchangeAlternateName).build();
    }

    /**
     * 备用队列
     * @return
     */
    @Bean
    public Queue queueAlternate() {
        return QueueBuilder.durable(queueAlternateName).build();
    }

    /**
     * 绑定 备用交换机和备用队列
     * @param alternateExchange
     * @param queueAlternate
     * @return
     */
    @Bean
    public Binding bindingAlternate(FanoutExchange alternateExchange, Queue queueAlternate) {
        return BindingBuilder.bind(queueAlternate).to(alternateExchange);
    }

}
